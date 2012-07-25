#!/usr/bin/python
#
# Script: dt_analyzer
# Purpose: Analyzes the output of parallel DT runs
# Author: Jochen Wuttke, wuttkej@gmail.com
# Date:
#
# Arguments:
#  --inputs <dir>   directory containing all used input files (for sanity checks)
#  --outputs <dir>  directory containing all outputs files from DT runs
#  --allTests <file> name of the file containing the results for the default test run

import argparse
import os

def filter_junit(string):
    n = ""
    for s in string.split(" - "):
        s = s.strip()
        #skip empty string (happens due to pythonic stuff)
        if (s == ""): continue
        #ignore everything after the first junit or dt stuff
        if (s.startswith("org.junit") or s.startswith("edu.washington.cs.dt.")):
            break
        #ignore static initializers
        if (s.find("<clinit>") >= 0):
            n = ""  #remove anything on the stack trace that's called by the static initializer
            continue
        n += s
        n += " - "
    return n

# WARNING: NEVER mutate the state of a TestResult object after creating it!
class TestResult:
    def __init__(self, f_string):
        self.name = ""
        self.result = "UNDEFINED"
        self.stack_trace = None
        self.name = f_string[0:f_string.index("#")]
        self.result = f_string[f_string.index("#")+1:f_string.index("%#%#")]
        if ( self.result != "PASS" ):
            self.stack_trace = filter_junit(f_string[f_string.index("%#%#")+4:])

    def __eq__(self,other):
        return self.name == other.name and self.result == other.result and self.stack_trace == other.stack_trace

    def __neq__(self, other):
        return not self == other

    def __str__(self):
        return self.name + ": " + self.result + ": " + str(self.stack_trace)

    def __hash__(self):
        return hash(self.name) + hash(self.result) + hash(self.stack_trace)

def load_results(f):
    '''
    Load the results from the file f, return a map from testname to result
    '''
    infile = open(f, "r")
    results = []
    for line in infile:
        r = TestResult(line)
        results.append(r)
    return results

def compare_and_report(all_tests, some_tests):
    '''
    Takes a map containing all test results and an ordered list
containing specific test results and check if they match
    '''
    not_matching = []
    for t in some_tests:
        if not t == all_tests[t.name]:
            not_matching.append(t)
    if ( len(not_matching) > 0):
        print "Tuple " + str([ t.name for t in some_tests]) + " has differing results"
        for t in not_matching:
            base = all_tests[t.name]
            print "\t" + base.name + ": Expected: " + base.result + "\tWas: " + t.result 
            if ( base.result == t.result and base.stack_trace != t.stack_trace): print "stack difference" 
        
    return not_matching

def summary_report(all_set, diff_set):
    print "\n\nSummary Report:\n---------------"
    print "Total dependent tests: " + str(len(diff_set))
    result_mismatch = 0
    stack_mismatch = 0
    for t in diff_set:
        if (all_set[t.name].result == t.result): stack_mismatch += 1
        else: result_mismatch += 1
    print "Total result mismatch: " + str(result_mismatch)
    print "Total stack mismatch: " + str(stack_mismatch)

    print "\nDependent tests:"
    for n in  [t.name for t in diff_set]:
        print n


def main(args):
    #TODO: sanity check that we have all expected outputs
    all_test_results = {}
    a = load_results(args.ALL_TESTS)
    for r in a:
        all_test_results[r.name] = r

    all_diff = set()
    for f in os.listdir(args.OUTPUT_DIR):
        file_results = load_results(args.OUTPUT_DIR + "/" + f)
        d = compare_and_report(all_test_results, file_results)
        all_diff = all_diff.union( set(d))
    
    summary_report(all_test_results, all_diff)
    return

def parse_arguments():
    '''
    Parse commandline arguments
    '''
    parser = argparse.ArgumentParser()
    parser.add_argument("--input", dest="INPUT_DIR", required=True)
    parser.add_argument("--output", dest="OUTPUT_DIR", required=True)
    parser.add_argument("--allTestRuns", dest="ALL_TESTS", default="alltests.output", required=False)
    return parser.parse_args()

if __name__ == "__main__":
    main(parse_arguments())
