from mcstatus import MinecraftServer
try:
        server = MinecraftServer.lookup("I removed my IP and Port")
        print("Server found")
except:
        print("Server Down")
try:
        latency = server.ping()
        print("The server replied in {0} ms".format(latency))
        print("Ping Works")
except:
        print("Server Down")
