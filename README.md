# Simple-Ticket-Plugin

## Purpose
This plugin is designed to take the complexity out of Minecraft in-game ticketing! With this plugin all you have to do is drop it into the plugins folder and configure the three permission nodes. The goal of the plugin was to avoid the clutter and the complexity of other ticketing solutions. Rather this plugin takes out the complexity only having a few simple and easy to use commands!

## Setup
To setup this plugin download the .jar from the spigot page. Put this plugin into the plugins folder for your spigot server. When the plugin is loaded for the first time a folder and config file will be created. Within the config file is the initial message that is sent when a player opens a ticket, you can change this to whatever you like!

## Commands
There are only 4 commands within the entire plugin. Below are the commands and what they do. As usual there is tab complete on the command with args. 

### /ticket (claim, close, delete) (ticket number) : aliases (tr)
- The main command of the plugin. The subcommands are used by ticket.ticket.staff and otherwise the command usage is /ticket (message) as this replies to your currently open ticket.
![Screenshot](Images/history.png)

### /newticket : aliases (nt, ntick)
- allows a user to open a new ticket, will also send the below alert broadcast to all those with ticket.ticket.staff
![Screenshot](Images/alert.png)

### /treload aliases (ticketreload)
- Pretty much what the name says, reloads the config files of the plugin

### /tickets : aliases (listtickets)
- Sends a list of all currently open tickets. The ticket.ticket.staff permission is needed to use this command.
![Screenshot](Images/list.png)

## Compatability
This plugin uses the displayname of the user, so it works with both rank prefixes and with colored nicknames. This means that any staff/moderators that may use this plugin could keep their displaynames so players could be certain that they are speaking with staff.

### Showcase of server with no changes to displayname
![Screenshot](Images/message1.png)

### Showcase of server with chat prefixes and different displaynames
![Screenshot](Images/message2.png)

## Permissions
There are only three permissions for the plugin. This is part of keeping the plugin simple and easy to use. 
- ticket.ticket.staff : This is the staff permission node and allows for the claiming, history, and close subcommands to be used. It also allows the /tickets to be used.
- ticket.ticket : This allows people to open new tickets and respond to their ticket (use of /newticket and /ticket) 
- ticket.ticket.reload : This allows for the config file to be reloaded

## Status
- This plugin is still in development and will have many new features in the near future
