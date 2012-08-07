#!/usr/bin/python
#
# Script:
# Purpose:
# Author: Jochen Wuttke, wuttkej@gmail.com
# Date:

import os

def strip_suffix(item, suffix):
    return item[:-len(suffix)]

def main():
    ins = [strip_suffix(f, ".input") for f in  os.listdir("input")]
    ins.sort()
    outs = [strip_suffix(f, ".output") for f in os.listdir("output")]
    outs.sort()   

    i = 0
    offset = 0
    while( i < len(ins) ):
        if ( (i - offset) >= len(outs) or ins[i] != outs[i  - offset] ):
            print "Missing output: " + ins[i]
            offset +=1
        i += 1

    return

if __name__ == "__main__":
    main()
