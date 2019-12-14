#!/bin/bash
sh D:/Desktop/temp/ushttp.sh;
sh D:/Desktop/temp/ssl.sh;
sh D:/Desktop/temp/nova.sh;
sh D:/Desktop/temp/gmp.sh;
sh D:/Desktop/temp/id.sh;
cat D:/Desktop/temp/p.txt <(echo) D:/Desktop/temp/ssl.txt <(echo) D:/Desktop/temp/nova.txt <(echo) D:/Desktop/temp/g.txt <(echo) D:/Desktop/temp/id.txt | sed '/^$/d' > D:/Desktop/temp/all.txt
