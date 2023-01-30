#!/bin/bash

#Script usado para transformar arquivo .grf em um texto como lista 
#(cursentence.grf, que é gerado pelo Unitex na opção FST-Text),
#O arquivo de origem deve ser passado como parâmetro, seguido
#do arquivo de destino. Ex.: "./script cursentence.grf destino.txt"

sed '1,22 d;s/"//g;s/ .*$//' "$1" > temp

NUM=$(wc -l < temp)
CONT=1

> "$2"

while [ $CONT -le $NUM ]
do
	A=$(sed -n  "$CONT p" temp)
	if [[ "${A:0:1}" != '{' ]]
	then
		A=$(echo "$A" | sed 's/\/.*$//')
		if [ $A != "." ] && [ $A != "," ] && [ $A != "?" ] && [ $A != "!" ]
		then
			A="{"$A"}"
		fi
		echo $A >> "$2"
	else
		AUX=$(echo "$A" | cut -d "/" -f2)
		C=$(sed -n "/\/$AUX$/p" temp| sed -n '$=')
		
		if [ $C -gt 1 ]
		then
			A=$(echo "$A" | sed 's/\/.*$//')

			echo "($A|" >> "$2"
			CO=1
			
			while [ $CO -lt `expr "$C" - 1` ]
			do
				CONT1=`expr "$CONT" + "$CO"`
				CO=`expr "$CO" + 1`
				A1=$(sed -n  "$CONT1 p" temp)
				A1=$(echo "$A1" | sed 's/\/.*$//')
				echo "$A1|" >> "$2"
			done
			
			B=`expr "$CONT" + "$C" - 1`
			A2=$(sed -n "$B p" temp)
			A2=$(echo "$A2" | sed 's/\/.*$//')
			echo "$A2)" >> "$2"
			
		else
			A=$(echo "$A" | sed 's/\/.*$//')
			echo $A >> "$2"
		fi
		
		CONT=`expr $CONT + $C - 1`
	fi
	
	
	CONT=`expr "$CONT" + 1`
done

sed ':a;$!N;s/\n/ /;ta;' "$2" > temp
cat temp | sed 's;| ;|;g' > "$2"

rm temp


