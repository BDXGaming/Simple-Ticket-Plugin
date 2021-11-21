# Simple-Ticket-Plugin (Pre Release)

## Purpose
This plugin is designed to take the complexity out of Minecraft in-game ticketing! With this plugin all you have to do is drop it into the plugins folder and configure the three permission nodes. The goal of the plugin was to avoid the clutter and the complexity of other ticketing solutions. Rather this plugin takes out the complexity only having a few simple and easy to use commands!

## Setup
To setup this plugin download the .jar from the spigot page. Put this plugin into the plugins folder for your spigot server. When the plugin is loaded for the first time a folder and config file will be created. Within the config file is the initial message that is sent when a player opens a ticket, you can change this to whatever you like!

## Commands
There are only 4 commands within the entire plugin. Below are the commands and what they do. As usual there is tab complete on the command with args. 

### /ticket (claim, close, delete) (ticket number) : aliases (tr)
- The main command of the plugin. The subcommands are used by ticket.ticket.staff and otherwise the command usage is /ticket (message) as this replies to your currently open ticket.
![history](https://user-images.githubusercontent.com/74644043/120880229-ffa98c80-c596-11eb-9090-e93f9c8682fd.png)

### /newticket : aliases (nt, ntick)
- allows a user to open a new ticket, will also send the below alert broadcast to all those with ticket.ticket.staff
![alert](https://user-images.githubusercontent.com/74644043/120880238-118b2f80-c597-11eb-9793-c18a4abc7729.png)


### /treload aliases (ticketreload)
- Pretty much what the name says, reloads the config files of the plugin

### /tickets : aliases (listtickets)
- Sends a list of all currently open tickets. The ticket.ticket.staff permission is needed to use this command.
![list](https://user-images.githubusercontent.com/74644043/120880249-2a93e080-c597-11eb-9c9e-1cab44c4214e.png)

### Punishment Commands
- These are used to prevent users from abusing the ticketing system
- All users with ticket.ticket.staff permission will receive a broadcast upon any punishment or removal of punishment

### /tpunish <user> <duration>
- Prevents users from opening tickets for the duration provided 
- Supports m, h, d time notations 
  
### /tunpunish <user>
- Allows a previously punished user to once again open tickets 


## Compatability
This plugin uses the displayname of the user, so it works with both rank prefixes and with colored nicknames. This means that any staff/moderators that may use this plugin could keep their displaynames so players could be certain that they are speaking with staff.

### Showcase of server with no changes to displayname
![message1](https://user-images.githubusercontent.com/74644043/120880252-3089c180-c597-11eb-9a24-154eb0d56dd5.png)

### Showcase of server with chat prefixes and different displaynames
![message2](https://user-images.githubusercontent.com/74644043/120880255-3a132980-c597-11eb-962d-facdcb0fbfd2.png)


## Permissions
There are only 4 permissions for the plugin. This is part of keeping the plugin simple and easy to use. 
- ticket.ticket.staff : This is the staff permission node and allows for the claiming, history, and close subcommands to be used. It also allows the /tickets to be used.
- ticket.ticket.staff.clearhist : This is the required permission to clear a users punishment history.
- ticket.ticket : This allows people to open new tickets and respond to their ticket (use of /newticket and /ticket) 
- ticket.ticket.reload : This allows for the config file to be reloaded

## Status
- This plugin is still in development and will have many new features in the near future
