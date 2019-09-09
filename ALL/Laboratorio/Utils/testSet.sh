#!/bin/bash
INPUTS="$(find TestSet/input*)"
OPT="output"

for INPUT in ${INPUTS}
do
	echo ${INPUT}
	OUTPUT=${INPUT//input/$OPT}
	echo $OUTPUT
	DIFF="$(./$1 < ${INPUT} | diff - ${OUTPUT})"
	echo ${DIFF}
done
