Requires gradle and java 8


Usage:   `gradle run -D exec.args="DataAndExtractsFolderParentPath"`

Notes about program:

`DataAndExtractsFolderParentPath` should contain `data` and `extracts` folders in it.

Program walks through data folder and calculates ranks for each folder according to their `sents.txt` and `sims.mat` files. Then finds order by using these ranks via Max Marginal Relevance Algorithm.


1. It generates the results to the `results.txt` in project folder. 
2. `results.txt` file is deleted when re-run.
3. Overall scores and Max measures for one data folder is given below. (Taken from `results.txt`)


#####################################################
Overall measures: Recall:0,0819,	Precision:0,0698
#####################################################

#####################################################
Max measures:
Recall:Evaluation{docset='d074ba', size=400, recall=0,4667, precision=0,3182}
Precision:Evaluation{docset='d074ba', size=400, recall=0,4667, precision=0,3182}
#####################################################