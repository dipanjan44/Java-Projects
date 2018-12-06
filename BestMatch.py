import os
import re
from collections import Counter

# This is the map where dictionary terms will be stored as keys and value will be posting list with position in the file
dictionary = {}
# This is the map of docId to input file name
docIdMap = {}
# This is the map for the relevant documents
relevent_doc_map = {}

# Variable to map query id to each query
query_id = 1


def buildIndex (path):
    docId = 1
    fileList = [f for f in os.listdir (path) if os.path.isfile (os.path.join (path, f))]
    fileobj = open ('frequency.txt', 'w')
    for eachFile in fileList:
        position = 1
        count = 0
        # docName = "Doc_Id_" + str(docId)
        # docName =  str(docId)
        docIdMap[docId] = eachFile
        lines = [line.rstrip ('\n') for line in open (path + "/" + eachFile)]

        for eachLine in lines:
            wordList = re.split ('\W+', eachLine)

            while '' in wordList:
                wordList.remove ('')
            for word in wordList:
                if (word.lower () in dictionary):
                    postingList = dictionary[word.lower ()]
                    if (docId in postingList):
                        postingList[docId].append (position)
                        position = position + 1
                    else:
                        postingList[docId] = [position]
                        position = position + 1
                else:
                    dictionary[word.lower ()] = {docId: [position]}
                    position = position + 1
        docId = docId + 1
    # lengths = {key:len(value) for key,value in dictionary.iteritems()}
    length_dict = {key: len (value) for key, value in dictionary.items ()}
    for w in length_dict:
        fileobj.write (w + "   |   " + str (length_dict[w]))
        fileobj.write ("\n")
    fileobj.close ()
    for t in dictionary:
        poList = dictionary[t]
        kList = []
        for keys in poList:
            kList.append (keys)
        line = "       "

        for d in docIdMap:
            if d in kList:
                line = line + " | " + "1"
            else:
                line = line + " | " + "0"

def print_dict ():
    # function to print the terms and posting list in the index
    fileobj = open ("invertedIndex.txt", 'w')
    for key in dictionary.keys ():
        print (key + " --> " + str (dictionary[key]))
        fileobj.write (key + " --> " + str (dictionary[key]))
        fileobj.write ("\n")
    fileobj.close ()


def print_doc_list ():
    for key in docIdMap:
        print ("Doc ID: " + str (key) + " ==> " + str (docIdMap[key]))


def getRelevantDocuments (relevent_doc_map):
    for entry in relevent_doc_map.keys ():
        doc_list = []
        doc_list = relevent_doc_map.get (entry)

        for i in range (0, len (doc_list)):
            value = doc_list[i]
            doc_name = docIdMap.get (value)
            doc_list[i] = doc_name
        relevent_doc_map[entry] = doc_list
    return relevent_doc_map


def best_match_retrival (query, query_id):
    all_docs=getalldocuments(query)
    if all_docs is not None:
       relevent_doc_map[query_id]=all_docs
    else:
        print("There are no documents in the corpus for the given query")


def getalldocuments (query):
    result = []
    for term in query:
        if getPostingList(term) is not None:
            result += getPostingList (term)
    return list (set (result))


def getPostingList (term):
    if term in dictionary.keys ():
        postingList = dictionary[term]
        #print("The term is : " + str(term) + " => and posting list is : " +str(postingList))
        keysList = []
        for keys in postingList:
            keysList.append (keys)
        keysList.sort ()
        # print ("The keysList is : " +str(keysList))
        return keysList
    else:
        return None


def main ():
    # docCollectionPath = input("Enter path of text file collection : ")
    docCollectionPath = "/Users/dipanjan/gitHub/Python-Projects/FinalProject/ExtraCredit/test-data/raw-documents/"
    # queryFile = input("Enter path of query file : ")
    queryFile = "/Users/dipanjan/gitHub/Python-Projects/FinalProject/ExtraCredit/test-data/query.txt"
    # method to build the index
    buildIndex (docCollectionPath)

    print ("")
    print ("Inverted Index :")
    print_dict ()
    print ("")
    print ("Document List :")
    print_doc_list ()
    print ("")

    # method to extract the queries and populate the dictionary with document details
    QueryLines = [line.rstrip ('\n') for line in open (queryFile)]
    for eachLine in QueryLines:
        wordList = re.split ('\W+', eachLine)

        while '' in wordList:
            wordList.remove ('')

        wordsInLowerCase = []
        for word in wordList:
            global query_id
            wordsInLowerCase.append (word.lower ())
            # print(str(wordsInLowerCase))
        best_match_retrival(wordsInLowerCase, query_id)
        query_id = query_id + 1

    # Ranking by relevance
    relevant_documents = getRelevantDocuments(relevent_doc_map)

    print ("The relevant document list  is : " + str (relevant_documents))


if __name__ == '__main__':
    main ()