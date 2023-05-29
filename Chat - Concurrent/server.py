#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Mar  11 14:55:40 2019

@author: mo3tamed
"""

from socket import * 

from threading import Thread

clients  = {}

def accept_connections():
    while True:
        con , add = s.accept() 
        print('{} has entered chat'.format(add[1])  )
        con.send("Welcome to MO3ta Chat, plz enter name ".encode('utf-8'))	
        Thread(target=handleClient ,args=[ con, ]).start() # start thread to handle client
        		          
	    

def handleClient(con):
    name = con.recv(1024)
    name = name.decode("utf-8")
    print(name)
    msg =  "welcome " + name + " write {quite} to exit form chat"
    con.send(msg.encode("utf-8"))
    clients[con] = con
    sendToAll("{} has entered chat".format(name) , "" , con1 = con ) 
    try:
        while 1:
            msg = con.recv(BUFF_SIZE)
            msg = msg.decode('utf-8')
            print(name , msg , sep = ":")
            if msg != "{quite}":
                sendToAll( msg  ,name  , con1 = con)
            else :
                con.send("{quite}".encode("utf-8"))
                del clients[con] # remove from set
                sendToAll(" {} has left chat".format(name), "" , con1 = None)
                con.close()
                break
    except:
            sendToAll(" {} has left chat".format(name), "" , con1 = None)
 
def sendToAll(msg , src  , con1): # keep track of no-defaut must be last 
    src = src +"::"+ msg
    msg = src.encode("utf-8")
    for con in clients:
        if con != con1:
             con.send(msg)

try:
    s = socket(AF_INET , SOCK_STREAM)
    s.setsockopt(SOL_SOCKET , SO_REUSEADDR , 1)    
    HOST = '127.0.0.1'
    PORT = 7788
    s.bind((HOST , PORT))
    s.listen(5)
    BUFF_SIZE = 1024  
    
    t = Thread(target = accept_connections )
    t.start()
    t.join()
    s.close()
    
    print("exit")
except:
    print("bye")
    exit()    
