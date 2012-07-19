#!/usr/bin/python
#
# Script: create_input_files.py
# Purpose: Takes a file with a list of test cases and produces 
#          output files with all selected permutations up to lenght k
# Author: Jochen Wuttke, wuttkej@gmail.com
# Date: 2012-06-17
#
# Usage:
# create_input_files.py -i test.file -k limit
# test.file is the file containing all test names
# the integer limit determines how long the generated sequences are

import argparse
import os
from os.path import exists

def write_file(names, i):
    outfile = open( "input/" + str(i) + ".input", "w" )
    for n in names:
        outfile.write(n)
    outfile.close()

def create_permutations(depth, lower, upper, tests, current, iteration):
    #print "Creating permutations for tests " + str(depth) + ":" + str(limit)
    #print current
    it = iteration
    for t in tests:
        if ( not current.__contains__(t) ): 
            if ( depth < upper ): 
                current.append(t)
                if ( len(current) >= lower ): write_file(current, it)
                it = create_permutations(depth+1, lower, upper, tests, current, it + 1 )
                current.remove(t)
    return it

def parse_arguments():
    parser = argparse.ArgumentParser()
    parser.add_argument("-i", "--input-file", dest="IN_FILE", required=True, help="The file containing all tests to be processed")
    parser.add_argument("-K", "--upper-bound", dest="UPPER_BOUND", required=True, help="The limit for the length of the computed permutations")
    parser.add_argument("-k", "--lower-bound", dest="LOWER_BOUND", required=False, help="The lower limit for the length of the computed permutations", default=1 )
    return parser.parse_args()

def main(args):
    if ( not exists("input") ): os.mkdir("input")
    infile = open(args.IN_FILE, "r")
    tests = infile.readlines()
    create_permutations(0, int(args.LOWER_BOUND), int(args.UPPER_BOUND), tests, [], 1 )
    return

if __name__ == "__main__":
    args = parse_arguments()
    main(args)
