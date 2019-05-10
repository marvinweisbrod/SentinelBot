package Wrappers;

import java.util.List;
import java.util.Map;

import Discord.Constants;
import Discord.IO;
import Discord.URI_Handler;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IEmbed;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

public class EmbedWrapper {
	private IEmbed embed;
	private Metadata metadata;
	
	public static final int POLLTYPE_default = 0;
	
	public EmbedWrapper(IEmbed embed) {
		this.embed = embed;
		metadata = URI_Handler.getFakeUriContent(embed.getAuthor().getUrl());
	}
	
	public Metadata getMetadata(){
		return metadata;
	}
	
	public IEmbed getEmbed() {
		return embed;
	}
	
	public static class Metadata {
		public static enum Type {NONE, HELP, POLL};
		public Type type = Type.NONE;
		public long uid = 0;
		public int extraInt = 0;
	}
	
	public static EmbedObject CreateFirstPollMessage(IUser author, IGuild guild, String content, int type, String authortext) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorName(authortext);
		builder.withAuthorUrl(URI_Handler.createFakeUri_Poll(author.getLongID(), type));
		builder.withColor(Constants.POLL_EMBED_COLORS[0],Constants.POLL_EMBED_COLORS[1],Constants.POLL_EMBED_COLORS[2]);
	    builder.withDescription(content + "\n");
	    String numberCheck = " (" + 0 + "/" + 0 + ")";
	    String numberX = " (" + 0 + "/" + 0 + ")";
	    String peopleYes = "-----";
	    String peopleNo = "-----";
	    builder.appendField(Constants.REACTION_CHECK + numberCheck, 
	    		peopleYes, true);
	    builder.appendField(Constants.REACTION_X + numberX, 
	    		peopleNo, true);
	    return builder.build();
	}
	
	public static EmbedObject CreateFirstPollMessage(IUser author, IGuild guild, String content, int type) {
		return CreateFirstPollMessage(author, guild, content, type, author.getDisplayName(guild)+Constants.POLL_ADDITION);
	}
	
	public EmbedObject createNewPollEmbedFromPrevious(List<Long> usersYes, List<Long> usersNo, IGuild guild) {
	    
	    // Build strings for voters
	    String peopleYes = "";
	    String peopleNo = "";
	    
	    for(Long l : usersYes)
	    	peopleYes = peopleYes + "<@" + String.valueOf(l) + ">\n";
	    for(Long l : usersNo)
	    	peopleNo = peopleNo + "<@" + String.valueOf(l) + ">\n";
	    
	    if(usersYes.isEmpty()) peopleYes = "-----";
	    if(usersNo.isEmpty()) peopleNo = "-----";
	    
		//Creating embed object
	    EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorName(embed.getAuthor().getName());
		builder.withAuthorUrl(embed.getAuthor().getUrl());
		
		builder.withColor(Constants.POLL_EMBED_COLORS[0],Constants.POLL_EMBED_COLORS[1],Constants.POLL_EMBED_COLORS[2]);
	    builder.withDescription(embed.getDescription());
	    int total = usersYes.size() + usersNo.size();
	    String numberCheck = " (" + usersYes.size() + "/" + total + ")";
	    String numberX = " (" + usersNo.size() + "/" + total + ")";
	    builder.appendField(Constants.REACTION_CHECK + numberCheck, 
	    		peopleYes, true);
	    builder.appendField(Constants.REACTION_X + numberX, 
	    		peopleNo, true);
	    
	    return builder.build();
	}
	
	//public String returnEmbedContent()
}
