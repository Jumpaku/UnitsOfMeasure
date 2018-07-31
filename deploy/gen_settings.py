#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys

"""
usage: ./gen_settings.py GITHUB_USERNAME GITHUB_PASSWORD < ./settings.xml > /usr/share/java/maven-3/conf/settings.xml
"""
if __name__ == '__main__':
    args = sys.argv
    if len(args) != 3:
        exit(1)
        
    while True:
        try:
            line = input()
            line = line.replace(
                "<username><!--INPUT_GITHUB_USERNAME--></username>",
                "<username>" + args[1] + "</username>")
            line = line.replace(
                "<password><!--INPUT_GITHUB_PASSWORD--></password>",
                "<password>" + args[2] + "</password>")
            print(line)
        except EOFError:
            break