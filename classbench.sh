echo "input rule number"
read RULENUM
echo "input field number (1 <= n <= 6)"
read FIELD
echo "input a file in parameter_files"
read PARAMETER
cd db_generator
./db_generator -bc ../parameter_files/$PARAMETER $RULENUM 2 0.5 -0.1 MyFilters
echo "input header number"
read HEADERNUM
cd ../trace_generator
./trace_generator 1 0.1 `expr $HEADERNUM / $RULENUM` ../db_generator/MyFilters
cd ..
echo "input rule name"
read RULENAME 
case "$FIELD" in 
    "1") cat db_generator/MyFilters | awk -F'\t' 'BEGIN{OFS="\t"} {print $1}' > $RULENAME
	 ;;
    "2") cat db_generator/MyFilters | awk -F'\t' 'BEGIN{OFS="\t"} {print $1,$2}' > $RULENAME
	 ;;
    "3") cat db_generator/MyFilters | awk -F'\t' 'BEGIN{OFS="\t"} {print $1,$2,$3}' > $RULENAME
         ;;
    "4") cat db_generator/MyFilters | awk -F'\t' 'BEGIN{OFS="\t"} {print $1,$2,$3,$4}' > $RULENAME
	 ;;
    "5") cat db_generator/MyFilters | awk -F'\t' 'BEGIN{OFS="\t"} {print $1,$2,$3,$4,$5}' > $RULENAME
	 ;;
    "6") cat db_generator/MyFilters | awk -F'\t' 'BEGIN{OFS="\t"} {print $1,$2,$3,$4,$5,$6}' > $RULENAME
	 ;;
esac
echo "input header name"
read HEADERNAME
case "$FIELD" in 
    "1") cat db_generator/MyFilters_trace | awk 'BEGIN{OFS="\t"} {print $1}' > $HEADERNAME
	 ;;
    "2") cat db_generator/MyFilters_trace | awk 'BEGIN{OFS="\t"} {print $1,$2}' > $HEADERNAME
	 ;;
    "3") cat db_generator/MyFilters_trace | awk 'BEGIN{OFS="\t"} {print $1,$2,$3}' > $HEADERNAME
         ;;
    "4") cat db_generator/MyFilters_trace | awk 'BEGIN{OFS="\t"} {print $1,$2,$3,$4}' > $HEADERNAME
	 ;;
    "5") cat db_generator/MyFilters_trace | awk 'BEGIN{OFS="\t"} {print $1,$2,$3,$4,$5}' > $HEADERNAME
	 ;;
    "6") cat db_generator/MyFilters_trace | awk 'BEGIN{OFS="\t"} {print $1,$2,$3,$4,$5,$6}' > $HEADERNAME
	 ;;
esac
