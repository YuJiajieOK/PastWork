import json
import os
import re


#The functions down there is a traditional way to split certain string from text.
def Fname_process(name):
    fname = ""
    for i in range(len(name)):
        if name[i]== ' ':
            for j in range(i+1,len(name)-1):
                fname = fname + name[j]
            break
    return(fname)
def Gname_process(name):
    gname = ""
    for i in range(len(name)):
        if name[i]!= ' ':
           gname = gname+name[i]
        else:
            break
    return gname
def Phone_process(phone):
    phoneNum = ""
    for i in range(len(phone)):
        if phone[i]==':':
            for j in range(i+1,len(phone)-1):
                phoneNum = phoneNum+phone[j]
            break
    return (phoneNum)

def Email_process(email):
    emailAdd= ""
    for i in range(len(email)):
        if email[i]==':':
            for j in range(i+1,len(email)-1):
                emailAdd = emailAdd+email[j]
            break
    return (emailAdd)

#Load json data and text to memory
jsondata = json.loads(open('data.json').read())
countryCodeList = json.loads(open('countrycode.json').read())# Here, to make it easier, I am using an external json file with all the country codes
f = open('raw.txt')
x = f.readlines()
#Call the function to split certain data from text
familyname = Fname_process(x[0])
givename = Gname_process(x[0])
telNumber = Phone_process(x[1])
emaileAddress = Email_process(x[2])

#Here is using Regular Expression and normal python built function to split address information
splitedAddress = re.split(r'\.|:',x[3])
postalcode = splitedAddress[2]
addressline = splitedAddress[1]
country = splitedAddress[1].split(',')[len(splitedAddress)].strip()
countryCode = countryCodeList[country]

# Almost there, integrated all the available information
jsondata['hrJson']['GivenName'] = givename
jsondata['hrJson']['FamilyName'] = familyname
jsondata['hrJson']['Phone']['items']['properties']['Number'] = telNumber
jsondata['hrJson']['Phone'] = [jsondata['hrJson']['Phone']['items']['properties']]
jsondata['hrJson']['Email']['items']['properties']['Address']= emaileAddress
jsondata['hrJson']['Email'] = [jsondata['hrJson']['Email']['items']['properties']]
jsondata['hrJson']['Address']['items']['properties']['PostalCode'] = postalcode
jsondata['hrJson']['Address']['items']['properties']['Addressline'] = addressline
jsondata['hrJson']['Address']['items']['properties']['CountryCode'] = countryCode

# Collecting available information to an output. In order to get rid of the unavailable information
outputs = {}
outputs['hrJson'] = {}
outputs['hrJson']['FamilyName'] = []
outputs['hrJson']['FamilyName'].append(jsondata['hrJson']['FamilyName'] )
outputs['hrJson']['Email'] = []
outputs['hrJson']['Email'].append(jsondata['hrJson']['Email'])
outputs['hrJson']['Address'] = {}
outputs['hrJson']['Address']['PostalCode'] = []
outputs['hrJson']['Address']['PostalCode'].append(jsondata['hrJson']['Address']['items']['properties']['PostalCode'] )
outputs['hrJson']['Address']['Addressline'] = []
outputs['hrJson']['Address']['Addressline'].append(jsondata['hrJson']['Address']['items']['properties']['Addressline'])
outputs['hrJson']['Address']['CountryCode'] = []
outputs['hrJson']['Address']['CountryCode'].append(jsondata['hrJson']['Address']['items']['properties']['CountryCode'])
outputs['hrJson']['Phone'] = []
outputs['hrJson']['Phone'].append(jsondata['hrJson']['Phone'])

#Here we are, let's output it
with open('outputs.txt', 'w') as outfile:
    json.dump(outputs, outfile)

print(outputs)




