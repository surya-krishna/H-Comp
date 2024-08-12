import json
from socket import SocketIO

from flask import Flask, redirect, url_for, request, Response, stream_with_context, session, after_this_request,g
from google.generativeai import ChatSession

from gemini import gemini
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import PickleType
import asyncio
from datetime import timedelta


app = Flask(__name__)

app.secret_key = 'supersecretkey'
# Configure MySQL database connection
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root:password@localhost:3306/wellness'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

# Configure session lifetime
app.config['PERMANENT_SESSION_LIFETIME'] = timedelta(minutes=30)

db = SQLAlchemy(app)
loop = asyncio.new_event_loop()
asyncio.set_event_loop(loop)


# Define a simple model for session data
class SessionData(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    key_var = db.Column(db.Integer)
    data = db.Column(PickleType)  # Using PickleType to store non-serializable data

    def __init__(self, key_var, data):
        self.key_var = key_var
        self.data = data

# Function to serialize and deserialize objects
def serialize(obj):
    import pickle
    return pickle.dumps(obj)

def deserialize(data):
    import pickle
    if data==None:
        return None
    return pickle.loads(data)


@app.route('/upload_file',methods=['POST'])
def upload_file():
    if request.method == 'POST':
        data = request.get_json()['data']
        return gemini.upload_file(data)

@app.route('/delete_file',methods=['DELETE'])
def delete_file():
    if request.method == 'DELETE':
        filename = request.args.get('filename')
        gemini.delete_files(filename)
        response={
            "status":"Success"
        }
        return response

@app.route('/extract_data',methods=['POST'])
def extract_data():
    if request.method == 'POST':
        data = request.get_json()['requestItems'][0]['files']
        print(data)
        return gemini.extract_content(data)

# @app.route('/chat5',methods=['POST'])
# def send_message2():
#     if request.method == 'POST':
#         data = request.get_json()['message']
#         chat = None
#         if ("data_id" not in session):
#             chat = gemini.create_chat("text")
#             print("chat1")
#             session_data = SessionData(data=chat)
#             db.session.add(session_data)
#             db.session.commit()
#             session['data_id'] = session_data.id
#             print("chat12")
#         else:
#             data_id = session.get('data_id')
#             if data_id:
#                 chat = ChatSession(SessionData.query.get(data_id))
#
#         async def event_stream(chat):
#             print("chat3")
#             if(chat != None):
#                 print(chat)
#                 async for line in await gemini.send_message(chat,data):
#                     print(line)
#                     text = line.text
#                     if len(text):
#                         yield text
#
#         async def collect_results(async_gen):
#             results = []
#             async for item in async_gen:
#                 results.append(item)
#             return results
#
#         def sync_event_stream(chat):
#             loop = asyncio.new_event_loop()
#             asyncio.set_event_loop(loop)
#             async_gen = event_stream(chat)
#             results = loop.run_until_complete(collect_results(async_gen))
#             for result in results:
#                 yield result
#
#         print("chat4")
#         return Response(sync_event_stream(chat), mimetype='text/plain')
#
#
#
#
#
# @app.route('/chat3',methods=['POST'])
# def send_message3():
#     if request.method == 'POST':
#         data = request.get_json()['message']
#         chat = None
#         if ("chat" not in session):
#             chat = gemini.create_chat("text")
#             print("chat1")
#             session['chat'] = serialize(chat)
#             print("chat12")
#         else:
#             chatUn = session.get('chat')
#             if chatUn:
#                 chat = deserialize(chatUn)
#
#
#
#         async def event_stream(chat):
#             print("chat3")
#             if(chat != None):
#                 async for line in await gemini.send_message(chat,data):
#                     text = line.text
#                     if len(text):
#                         yield text
#
#         async def collect_results(async_gen):
#             results = []
#             async for item in async_gen:
#                 results.append(item)
#             return results
#
#         def sync_event_stream(chat):
#             async_gen = event_stream(chat)
#             results = loop.run_until_complete(collect_results(async_gen))
#             for result in results:
#                 yield result
#
#         print("chat4")
#         response = Response(sync_event_stream(chat), mimetype='text/plain')
#
#         print(chat.history)
#         session['chat'] = serialize(chat)
#         return response
#


# @app.route('/chat',methods=['POST'])
# def send_message():
#     if request.method == 'POST':
#         data = request.get_json()['message']
#         chat = None
#         history = None
#
#         if ("history" not in session):
#             print("chat1")
#             chat = gemini.create_chat(history)
#             session['history'] = []
#             history = []
#             print("chat12")
#         else:
#             history = session.get('history')
#             print(history)
#             print(session)
#             chat = gemini.create_chat(history)
#
#
#         async def event_stream(chat):
#             print("chat3")
#             if(chat != None):
#                 async for line in await gemini.send_message(chat,data):
#                     text = line.text
#                     if len(text):
#                         yield text
#
#         async def collect_results(async_gen):
#             results = []
#             async for item in async_gen:
#                 results.append(item)
#             return results
#
#         @stream_with_context
#         def sync_event_stream(chat):
#             async_gen = event_stream(chat)
#             results = loop.run_until_complete(collect_results(async_gen))
#             for result in results:
#                 session['history'] = chat.history
#                 print(session['history'])
#                 print(session)
#                 yield result
#
#
#
#
#         print("chat4")
#         response = Response(sync_event_stream(chat), mimetype='text/plain')
#
#
#         return response
#


@app.route('/chat',methods=['POST'])
def send_message():
    if request.method == 'POST':
        print(request.get_json())
        data = request.get_json()['message']
        key = request.get_json()["consultationId"]
        #initialMsg = request.get_json()['initialMsg']
        chat = None
        session_data = SessionData.query.filter_by(key_var=key).first()
        history = None
        if session_data:
            history = deserialize(session_data.data)
        session.clear()
        #if (initialMsg=="1" or history==None):
        if (history == None):
            print("chat1")
            session['history'] = serialize([])
            history = []
            chat = gemini.create_chat(history)
            print("chat12")
        else:
            print(history)
            chat = gemini.create_chat(history)


        def event_stream(chat):
            print("chat3")
            if(chat != None):
                return gemini.send_message(chat,data)

        print("chat4")
        response = event_stream(chat)
        # response = {
        #     "text":response,
        #     "history": serialize(chat.history)
        # }
        session['history'] = serialize(chat.history)
        print(response)
        db.session.add(SessionData(key,serialize(chat.history)))
        db.session.commit()
        return Response(response, mimetype='text/plain')




@app.route('/chat_function',methods=['POST'])
def send_message_get_data():
    if request.method == 'POST':
        print(request.get_json())
        data = request.get_json()['message']
        key = request.get_json()["consultationId"]
        token = request.get_json()["token"]
        data = {"message":data,
                "consultation_id":key,
                "token":token}
        gemini_instance = gemini()
        #initialMsg = request.get_json()['initialMsg']
        chat = None
        session_data = SessionData.query.filter_by(key_var=key).first()
        history = None
        if session_data:
            history = deserialize(session_data.data)
        session.clear()
        #if (initialMsg=="1" or history==None):
        if (history == None):
            print("chat1")
            session['history'] = serialize([])
            history = []
            chat = gemini_instance.create_chat_data(history)
            print("chat12")
        else:
            print(history)
            chat = gemini_instance.create_chat_data(history)


        def event_stream(chat):
            print("chat3")
            if(chat != None):
                print(data)
                return gemini_instance.send_message_get_data(chat,data)

        print("chat4")
        response = event_stream(chat)
        # response = {
        #     "text":response,
        #     "history": serialize(chat.history)
        # }
        session['history'] = serialize(chat.history)
        print(response)
        print(chat.history)
        if session_data:
            session_data.data = serialize(chat.history)
        else:
            db.session.add(SessionData(key, serialize(chat.history)))
        db.session.commit()
        return Response(response, mimetype='text/plain')


@app.route('/chat2',methods=['POST'])
def generate_content():
    if request.method == 'POST':
        data = request.get_json()['message']
        print(data)
        return Response(gemini.generate_content(data), mimetype='text/plain')

@app.route('/stream')
def stream():
    session.clear()
    return Response(gemini.generate_large_data("text"), mimetype='text/plain')



if __name__ == '__main__':
    app.run()
