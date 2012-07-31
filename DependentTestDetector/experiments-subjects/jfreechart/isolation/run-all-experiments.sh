#!/bin/bash
#
#Script: run-all-experiments.sh
#Purpose: Wrapper around our analysis tools that will create a 
#         separate cluster process for each unique test combination
#         and submit it to job control.
#Project: Dependent test detection
#Author: Jochen Wuttke	wuttkej@gmail.com
#Date: 2012-07-09
#Version: 1
#
# Arguments: 
#    the JAR file to be analysed
#    a positive (>0) integer value


cluster_command=""
get_test_command="java -cp dt-detector-0.2.0.jar edu.washington.cs.dt.tools.UnitTestFinder --pathOrJarFile $1 --junit3and4=true"
dt_command="java -cp ../../../../../dt-detector-0.2.0.jar:$1:lib/* edu.washington.cs.dt.util.TestRunnerWrapperFileInputs"
c_script=../../resources/scripts/create_input_files.py

#first, create all the directories and input files
#mkdir input
mkdir output
#$get_test_command
#$HOME/.local/bin/python $c_script -i allunittests.txt -k $2

#loop over all input files and launch process
for file in `ls input/`
do
  echo Scheduling $file
  sbatch <<-EOF
#!/bin/bash
#SBATCH --exclusive
echo Scheduling job `basename $file .input`
srun -t 10 $cluster_command $dt_command output/`basename $file .input`.output input/$file
EOF
done
