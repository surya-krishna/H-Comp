import base64
import json

import google.generativeai as genai
from PIL import Image
import io
from datetime import datetime
import random
import os
from flask import session, jsonify
import asyncio
import requests
import Levenshtein
from google.ai.generativelanguage_v1beta import Part
from google.generativeai.types import PartDict


class gemini:
    GOOGLE_API_KEY = "AIzaSyCX1kgUwXXEFIcQxPD-1oE_c-eHY9DSIRU"
    genai.configure(api_key=GOOGLE_API_KEY)
    spring_url = "http://localhost:8090"
    consultation_id = 0
    token = None
    def __init__(self):
        self.token = None
        self.consultation_id = 0

    def upload_file(image_data):
        # Assuming you have a byte array named 'image_bytes'
        image_bytes = base64.b64decode(image_data)
        image = Image.open(io.BytesIO(image_bytes))
        # Save the image (optional)
        file_name=str(datetime.today().strftime('%Y%m%d%H%M%S%f')+str(random.randint(1000, 9999)))+".jpg"
        image.save(file_name)
        sample_file = genai.upload_file(path=file_name, display_name="Report Image")
        if os.path.exists(file_name):
            os.remove(file_name)
        print(f"Uploaded file '{sample_file.display_name}' as: {sample_file.uri}")
        return sample_file.to_dict()

    def get_file(fileName):
        file_res = genai.get_file(name=fileName)
        return file_res

    def extract_content2(filesMetadata):
        # Open the images
        model = genai.GenerativeModel(model_name="models/gemini-1.5-flash",generation_config={"response_mime_type": "application/json"})
        prompt = """extract content from the lab report image using this JSON schema:
        {
            'labDetails':{
                'LabName':str,
                'LabAddress':str,
                'LabPhone':str
            },
            'patientDetails':{
                'PatientName':str,
                'PatientGender':str,
                'PatientAge':int,
                'ReportDate':str
            },
            labTestDetails:{
                'labTestName':str,
                'labTestValue':str,
                'labTestUnits':str,
                'normalRangeMinimumValue':float,
                'normalRangeMaximumValue':float
            }
        }
        """



        file_data=[]
        for data in filesMetadata:
            file_data.append(
                {
                    'mime_type':data['mime_type'],
                    'file_uri':data['uri']
                }
            )
        print(file_data)
        raw_response = model.generate_content(
            ["""Extract information from images of lab reports in JSON format describing features
            [LabName,LabAddress,LabPhoneNumber,PatientName,PatientGender,PatientAge,ReportDate,[labTestName,labTestValue,labTestUnits,normalRangeMinimumValue,normalRangeMaximumValue]]
            """]+file_data
        )
        response = json.loads(raw_response.text)
        print(response)
        #for fileContent in filesMetadata:
         #   self.delete_files(fileContent.name)
        return response


    def extract_content(filesMetadata):
        fileMetaList=[]
        print(filesMetadata)
        for data in filesMetadata:
            fileMetaList.append(genai.get_file(name=data['name']))

        # Open the images
        model = genai.GenerativeModel(model_name="models/gemini-1.5-flash")
        raw_response = model.generate_content(
            ["""Extract information from the images of lab reports, process each image individually. test details will be in tabular format of reports. mark containsPII 1 if contains data like patientName or else 0. ReportDate as yyyy-MM-dd format like 2024-12-20 and it is not BirthDate it is more like printed date or reported date or collection date.  while extracting give the proper normalmin normalmax values like <90 for male implies normal min 0 and normal max 90.extract information from images and mask most letters of PII data like patientName with X line if patientname is abcdefghijkl make it XXXXXXXXXjkl except for last 3 letters,  using the mentioned JSON format
            result = [{LabName:str,
            LabAddress:str,
            LabPhoneNumber:str,
            PatientName:str,
            PatientGender:str,
            PatientAge:int,
            ReportDate:str,
            testDetails:[{
            labTestName:str,
            labTestValue:str,
            labTestUnits:str,
            normalRangeMinimumValue:str,
            normalRangeMaximumValue:str}]
            }]
            
            return {
                response:result
                containsPII: boolean
            }
            """] + fileMetaList
        )
        print(raw_response.text[7:-3])
        response = json.loads(raw_response.text[7:-3])
        print(response)
        #for fileContent in filesMetadata:
         #   self.delete_files(fileContent.name)
        return response

    def delete_files(fileName):
        return genai.delete_file(fileName)

    async def send_message2(message):
        print("hello")
        model = genai.GenerativeModel('gemini-1.5-flash')
        '''if(session["chat"]==None):
            chat = model.start_chat(history=[])
            session["chat"]=chat
        chat = session["chat"]'''
        chat = model.start_chat(history=[])
        response = chat.send_message_async(message,stream=True)
        for chunk in response:
            yield chunk.text
            await asyncio.sleep(1)
        #response = chat.send_message_async(message)
        #print(response.text)

    def create_chat(history):
        model = genai.GenerativeModel('gemini-1.5-flash')
        chat = model.start_chat(history=history)
        chat.history
        return chat

    def send_message_async(chat,message):
        return chat.send_message_async(message,stream=True)


    def send_message(self,chat, message):
        #self.token=authToken
        reponse =  chat.send_message(message)
        return reponse.text
        #response = chat.send_message_async(message)
        #print(response.text)


    def create_chat_data(self,history):
        model = genai.GenerativeModel('gemini-1.5-flash',tools=[self.get_reports_list,self.get_test_details_by_testname,self.get_report_details,self.medicalSummarySuggestionsObservations,self.test_wise_recent_information])
        chat = model.start_chat(history=history,enable_automatic_function_calling=True)
        chat.history
        return chat

    def send_message_get_data(self,chat,data):
        self.consultation_id = data["consultation_id"]
        self.token = data["token"]
        reponse = chat.send_message(data["message"] + "{consultation_id : "+self.consultation_id
                                    +",token : "+self.token+"}")

        return reponse.text

    def generate_large_data(text):
        for i in range(1, 11):
            yield f"data chunk {i}\n"
            import time
            time.sleep(1)  # Simulate delay

    def generate_content(data):
        model = genai.GenerativeModel('gemini-1.5-flash')
        response = model.generate_content(data, stream=True)
        for chunk in response:
            print(chunk.text)
            yield chunk.text
            import time
            time.sleep(1)

    def get_reports_list(self,consultation_id:int,token:str):
        """get list of reports which can later be used to get details of particular report"""
        spring_url = "http://localhost:8090"
        body = {
            "consultationId":consultation_id,
            "lowerLimit":0,
            "count":1000
        }
        headers = {
            "Token": token,
            "content-type":"application/json"
        }
        response = requests.post(self.spring_url+'/user/getReportList',json = body,headers=headers)
        if response.status_code == 200:
            print("received response")
            data = response.json()
            print(data)
            return data
        else:
            return jsonify({'error': 'Failed to fetch data'}), response.status_code


    def medicalSummarySuggestionsObservations(self,consultation_id:int,token:str):
        """Summary of patient health condition by consolidation of all the reports including observations and suggestions by doctors"""
        headers = {
            "Token": token,
            "content-type":"application/json"
        }
        response = requests.get(self.spring_url+'/user/medicalSummary/'+str(int(consultation_id)),headers=headers)
        if response.status_code == 200:
            print("received response")
            data = response.json()
            print(data)
            return data
        else:
            return jsonify({'error': 'Failed to fetch data'}), response.status_code


    def test_wise_recent_information(self,consultation_id:int,token:str):
        """test wise most recent information"""
        print("recent values")
        headers = {
            "Token": token,
            "content-type":"application/json"
        }
        response = requests.get(self.spring_url+'/user/recentTestValues/'+str(int(consultation_id)),headers=headers)
        print("received response", response.text)
        if response.status_code == 200:
            print("received response",response.text)
            data = response.json()
            print(data)
            return data
        else:
            return jsonify({'error': 'Failed to fetch data'}), response.status_code



    def get_report_details(self,consultation_id:int,token:str,date:str,report_id:int=0):
        """display all the details in the report taken on particular date or with specific report id
        Args:
            date: date on which report was generated
            report_id: database id of reports which can be found in list of reports fetched
        """
        print("detailed report")
        body = {
            "reportId":report_id,
            "consultationId":consultation_id,
            "date":date
        }
        headers = {
            "Token": token,
            "content-type":"application/json"
        }
        response = requests.post(self.spring_url+'/user/getReportDetails',json = body,headers=headers)
        if response.status_code == 200:
            print("received response",response.text)
            data = response.json()
            print(data)
            return data
        else:
            return jsonify({'error': 'Failed to fetch data'}), response.status_code



    def get_test_details_by_testname(self,consultation_id:int,token:str,test_name:str):
        """display the detailed timeline and values of mentioned testname
        Args:
            testname: name of medical test that was taken earlier by the pateient
        """



        headers = {
            "Token": token,
            "content-type": "application/json"
        }
        response = requests.get(self.spring_url + '/user/recentTestValues/' + str(int(consultation_id)), headers=headers)
        testnames = []
        max_similarity = 0
        max_similar_test = None
        if response.status_code == 200:
            print("received response")
            data = response.json()
            for test in data:
                testnames.append(test["testName"])
                similarity = self.compute_levenshtein_similarity(test["testName"],test_name)
                if(similarity>max_similarity):
                    max_similarity=similarity
                    max_similar_test=test["testName"]

        body = {
            "testMasterId":0,
            "consultationId":consultation_id,
            "testName":max_similar_test
        }
        headers = {
            "Token": token,
            "content-type":"application/json"
        }
        response = requests.post(self.spring_url+'/user/getTestDetails',json = body,headers=headers)
        if response.status_code == 200:
            print("received response")
            data = response.json()
            print(data)
            return data
        else:
            return jsonify({'error': 'Failed to fetch data'}), response.status_code

    def compute_levenshtein_similarity(str1, str2):
        distance = Levenshtein.distance(str1, str2)
        max_len = max(len(str1), len(str2))
        similarity = 1 - distance / max_len
        return similarity




