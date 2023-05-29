from tkinter import *
from socket import * 

soc = None

name = "default"

def connectToChat():
    global soc
    global name
    name = username.get()
    soc = socket(AF_INET , SOCK_STREAM)
    soc.connect((ip.get() , int(port.get())))
    t = Thread(target = rec )
    t.start()
    text.insert(END , "Welecome {}".format(name))
    soc.send(name.encode("utf-8"))
    

def send():
    msg=msgToSend.get()
    # format msg to be sent
    messageToSend = "{}:{}".format(name,msg)
    messageToSend = messageToSend.encode("utf-8")
    soc.send(messageToSend)
    # add client msg to text
    addMsgTolayout("me:{}".format(msg))
    # empty fields
    msgToSend.delete(0,END)
    
    

#window
wind = Tk()
wind.title("Mo3ta Chat Room") # title

ip = Entry(wind )
ip.pack(pady= 3 )
ip.insert( 0 , "127.0.0.1" ) # add default ip 


port = Entry(wind)
port.pack( pady= 2)
port.insert(0 , "6002") # add default port 

username = Entry(wind)
username.pack( pady= 3)


btnConnect=Button(wind,text="Connect",command=connectToChat)
btnConnect.pack()


text = Text(wind ,height = 10 )
text.pack(fill=X )

msgToSend = Entry(wind)
msgToSend.pack()

btnSend=Button(wind,text="Send",command=send)
btnSend.pack()



from threading import Thread

def addMsgTolayout(msg):
    text.insert(END , '\n')
    text.insert(END , msg)
    
          

def rec():
    while True:
      msg = soc.recv(1024).decode("utf-8")
      addMsgTolayout(msg)
      

#to run the window
wind.mainloop()



    
