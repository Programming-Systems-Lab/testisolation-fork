#!/usr/bin/python
#
# Script: create_input_files.py
# Purpose: Takes a file with a list of test cases and produces 
#          output files with all selected permutations up to lenght k
# Author: Jochen Wuttke, wuttkej@gmail.com
# Date: 2012-06-17
#
# Usage:
# create_input_files.py -i test.file -K limit
# test.file is the file containing all test names
# the integer limit determines how long the generated sequences are
#
# Arguments:
# -k        The minimun length of tuples in the output files (optional, defaults to 1)
# -K        The maximum length of tuples in the output files. Mandatory.
# -i        The name of the file containing all test names
# -s        The number of files to output with this run of the program
# -S        The first tuple to output.
#
# Use -s and -S together to break generating input files into several steps.
# For example use:
# create_input_files.py -k 3 -K 3 -s 10000 -i input.names
# <run analysis on all inputs>
# create_input_files.pyt -k 3 -K 3 -s 10000 -S 10001 -i input.names
# 
# This will create up to 10000 input tuples in the first run, and create
# another 10000 tuples in the second run, starting with the first tuple that 
# logically follows in the sequence.


import argparse
import os
from os.path import exists
import sys

def write_file(names, i):
    outfile = open( "input/" + str(i) + ".input", "w" )
    for n in names:
        outfile.write(n)
    outfile.close()

def create_permutations(depth, lower, upper, tests, current, iteration, first, limit):
    #print "Creating permutations for tests " + str(depth) + ":" + str(limit)
    #print current
    if ( iteration >=  (first + limit) ): 
#        print "Exceeded limit: " + str(iteration) + " > " + str(first) + " + " + str(limit)
        return iteration
    it = iteration
    for t in tests:
        if ( not current.__contains__(t) ): 
            if ( depth < upper ): 
                current.append(t)
                if ( len(current) >= lower and it >= first): write_file(current, it)
                it = create_permutations(depth+1, lower, upper, tests, current, it + 1, first, limit )
                current.remove(t)
                if ( it >= first + limit): return it
    return it

def parse_arguments():
    parser = argparse.ArgumentParser()
    parser.add_argument("-i", "--input-file", dest="IN_FILE", required=True, help="The file containing all tests to be processed")
    parser.add_argument("-K", "--upper-bound", dest="UPPER_BOUND", required=True, help="The limit for the length of the computed permutations")
    parser.add_argument("-k", "--lower-bound", dest="LOWER_BOUND", required=False, help="The lower limit for the length of the computed permutations", default=1 )
    parser.add_argument( "-s", "--steps", dest="STEPS", default=sys.maxint, required=False, help="The number of output files to generate. Use with -S")
    parser.add_argument( "-S", "--start", dest="START", default = 1, required=False, help="The first file to output.")
    return parser.parse_args()

def main(args):
    if ( not exists("input") ): os.mkdir("input")
    infile = open(args.IN_FILE, "r")
    tests = infile.readlines()
    create_permutations(0, int(args.LOWER_BOUND), int(args.UPPER_BOUND), tests, [], 1, int(args.START), int(args.STEPS) )
    return

if __name__ == "__main__":
    args = parse_arguments()
    main(args)
