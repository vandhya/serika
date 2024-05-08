package com.jagrosh.jmusicbot.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.commands.OwnerCommand;
import net.dv8tion.jda.api.entities.User;

/**
 * Send messages through bot
 */
public class ChatCmd extends OwnerCommand
{
    private final Bot bot;

    public ChatCmd(Bot bot)
    {
        this.bot = bot;
        this.name = "chat";
        this.help = "sends message to a given user id";
        this.arguments = "[userid] [msg]";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event)
    {
        String[] msg = event.getArgs().split("\\s+", 2);
        if (msg.length<2)
        {
            event.replyError("Please Include a user id and a message to send");
            return;
        }
        try
        {
            User subject = bot.getJDA().retrieveUserById(msg[0]).complete();
            subject.openPrivateChannel().queue(pc -> pc.sendMessage(msg[1]).queue());
            event.replySuccess("Message successfully sent!");
        }
        catch(Exception e)
        {
            event.reply(event.getClient().getError()+" Error! args = " + msg[0] + " " + msg[1]);
        }
    }
}
