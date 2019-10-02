# import json
# import requests
# data = {"question_type":"choices","vote":"37511750"}
# data_json = json.dumps(data)
#
#
# s = requests.post('https://www.menti.com/api/vote/gxg32b3qzb')
# # identifier = s.json()['identifier']
# identifier = 'c470ff7eaabe7f5f7cf8d601cad60d3dd4cab89d8f53eb6aa7e745ff907e3263'
#
# headers = {'x-identifier': identifier}
#
# r = requests.post('https://www.menti.com/api/vote', json = data, headers = headers)
# print (r.text)

# from urllib.parse import urlencode
# from urllib.request import Request, urlopen
#
# url = 'https://www.menti.com/core/vote/a5a714eb703d' # Set destination URL here
# post_fields = {"question_type":"choices","vote":"37510938"}     # Set POST fields here
#
# request = Request(url, urlencode(post_fields).encode())
# json = urlopen(request).read().decode()
# print(json)


import json
import requests
from threading import Thread

id = input('id: ')
vote = input('vote: ')
number = input('number of votes: ')

data = {"question_type":"choices","vote":vote}
data_json = json.dumps(data)

id_r = requests.get('https://www.menti.com/core/objects/vote_ids/' + id)
admin_key = id_r.json()['questions'][0]['admin_key']
# if(id_r.json()['questions'][0]['type'] == 'choices'):
#     print ('choices: ' + id_r.json()['questions'][0]['choices'].text)
print (id)
def sendVote():
    s = requests.post('https://www.menti.com/core/identifier')
    identifier = s.json()['identifier']
    print (identifier)

    headers = {'x-identifier': identifier}

    r = requests.post('https://www.menti.com/core/vote/' + admin_key, json = data, headers = headers)
    print (r.text)

threads = []

for i in range(int(number)):
    process = Thread(target=sendVote)
    process.start()
    threads.append(process)

for process in threads:
    process.join()
