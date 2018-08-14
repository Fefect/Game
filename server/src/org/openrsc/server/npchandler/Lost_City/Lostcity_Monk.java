/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
package org.openrsc.server.npchandler.Lost_City;
import org.openrsc.server.Config;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.Quests;
import org.openrsc.server.model.World;
import org.openrsc.server.npchandler.NpcHandler;



public class Lostcity_Monk implements NpcHandler {



	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(Quests.LOST_CITY);
		if(q != null) {
			if(q.finished()) {
				questStarted(npc, owner);
			} else {
				switch(q.getStage()) {
					case 3:
						questStarted(npc, owner);
						break;
					default:
						noQuestStarted(npc, owner);
				}
			}
		} else {
			noQuestStarted(npc, owner);
		}
	}
	
	private void questStarted(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Greetings and welcome to entrana", "I must warn you", "there are strong monsters down there", "and the only way out is a magic door", "that takes you deep into the wilderness", "since you have no weapons this could be dangerous", "are you sure you want to go down?"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options107 = {"Yes, i'm strong!", "On second thoughts I might not"};
						owner.setBusy(false);
						owner.sendMenu(options107);
						owner.setMenuHandler(new MenuHandler(options107) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										accept(npc, owner);
										break;
									case 1:
										decline(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	
	private void accept(final Npc npc, final Player owner) {
		npc.blockedBy(owner);
		owner.setBusy(true);
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"May saradomin watch over you..."}) {
			public void finished() {
				owner.sendMessage("You climb down the ladder....");
				owner.teleport(428, 3380, false);
				owner.sendMessage("...and drop into the dark cave");
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
	
	private void decline(final Npc npc, final Player owner) {
		owner.setBusy(false);
		npc.unblock();
	}
	
	private void noQuestStarted(final Npc npc, final Player owner) {
		npc.blockedBy(owner);
		owner.setBusy(true);
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Sorry, its too dangerous down there", "I cannot allow you to enter"}) {
			public void finished() {
				owner.sendMessage("You need to complete the lost city quest to enter here");
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
	
	
	
	
	
}