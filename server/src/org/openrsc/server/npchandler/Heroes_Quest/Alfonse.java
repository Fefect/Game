/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
//NPC ID: 260  

package org.openrsc.server.npchandler.Heroes_Quest;
import org.openrsc.server.Config;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.Quests;
import org.openrsc.server.model.World;
import org.openrsc.server.npchandler.NpcHandler;



public class Alfonse implements NpcHandler 
{

	public void handleNpc(final Npc npc, final Player owner) throws Exception 
	{
	
		npc.blockedBy(owner);
		owner.setBusy(true);
		
		Quest q = owner.getQuest(Quests.HEROS_QUEST);
		Quest blackarm = owner.getQuest(Quests.JOIN_BLACKARM_GANG);
		Quest phoenix = owner.getQuest(Quests.JOIN_PHOENIX_GANG);
		
		if (q == null)
		{
			noQuestStarted(npc, owner);
		}
		else
		{
			if(q != null) 
			{
				if(blackarm != null && blackarm.finished()) 
				{
					noQuestStarted(npc, owner);
				} 
				else 
				{
					switch(q.getStage())
					{
						case 2:
							onCandleJob(npc, owner);
						break;
						case 3:
						case 4:
						case 5:
							World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Go to charlie the cook round the back", "He may have some gherkins for you"}) 
							{
								public void finished() 
								{
									owner.sendMessage("Alfonse winks");
									owner.setBusy(false);
									npc.unblock();
								}
							});
						break;
					}
				}
			} 
		}
	}
	

	private void noQuestStarted(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hi there"}, true) 
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Ahoy matey, welcome to the shrimp and parrot"}) 
				{
					public void finished() 
					{
						owner.setBusy(false);
						npc.unblock();
					}
				});
			}
		});
	}

	
	private void onCandleJob(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Welcome to the shrimp and parrot", "Would you like to order sir?"}, true) 
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Do you sell Gherkins?"}) 
				{
					public void finished() 
					{
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hmm ask charlie the cook round the back", "He may have some gherkins for you"}) 
						{
							public void finished() 
							{
								owner.incQuestCompletionStage(Quests.HEROS_QUEST);
								owner.sendMessage("Alfonse winks");
								owner.setBusy(false);
								npc.unblock();
							}
						});
					}
				});
			}
		});
	}
	
		
	
}