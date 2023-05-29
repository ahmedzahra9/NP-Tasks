from socket import *


soc = socket(AF_INET , SOCK_STREAM)

soc.bind(("127.0.0.1" , 6002) )

soc.listen(5)

from threading import Thread

clients = [] 

def handle(con , addr):
    clients.append(con)
    # recive name  then send  name to all clients (except sender ^_^)
    name = con.recv(1024).decode("utf-8")
    msg = "{}: has entered chat".format(name).encode('utf-8')
    sendToAll(msg, con)
    while True:
        msg = con.recv(1024)
        sendToAll(msg , con)


def sendToAll(msg , con):
    for client in clients:
        if con != client:
            client.send(msg)



while True:
    con ,ad = soc.accept()    
    t = Thread(target = handle , args = [con,ad] )
    t.start()    
