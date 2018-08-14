/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
//npc ID 437
package org.openrsc.server.npchandler.Plague_City;
import org.openrsc.server.Config;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.logging.Logger;
import org.openrsc.server.logging.model.eventLog;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.Quests;
import org.openrsc.server.model.World;
import org.openrsc.server.npchandler.NpcHandler;
import org.openrsc.server.util.DataConversions;



public class Edmond implements NpcHandler 
{

	public void handleNpc(final Npc npc, final Player owner) throws Exception 
	{
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(Quests.PLAGUE_CITY);
		if(q != null) 
		{
			if(q.finished()) 
			{
				finished(npc, owner);
			} 
			else 
			{
				switch(q.getStage())
				{
					case 0:
						noQuestStarted(npc, owner);
						break;
					case 1:
						questStarted(npc, owner);
						break;
					case 2:
						questStage2(npc, owner);
						break;
					case 3:
						questStage3(npc, owner);
					break;
					case 4:
						questStage4(npc, owner);
						break;
					case 5:
						questStage5(npc, owner);
						break;
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
						questStageRest(npc, owner);
					break;
					case 13:
						questStage13(npc, owner);
					break;
				}
			}
		} 
		else 
		{
			noQuestStarted(npc, owner);
		}
	}
	

	private void noQuestStarted(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hello old man"}, true)
		{
			public void finished()
			{
				World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
				{
					public void action()
					{
						owner.sendMessage("The man looks upset");
						World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"What's wrong?"})
						{
							public void finished()
							{	
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"I've got to find my daughter", "I pray that she's still alive"})
								{
									public void finished()
									{	
							
										World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
										{
											public void action()
											{
												final String[] options107 = {"What happened to her?", "Well, good luck with finding her"};
												owner.setBusy(false);
												owner.sendMenu(options107);
												owner.setMenuHandler(new MenuHandler(options107) 
												{
													public void handleReply(final int option, final String reply)
													{
														owner.setBusy(true);
														for(Player informee : owner.getViewArea().getPlayersInView())
														{
															informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
														}
														switch(option) 
														{
															case 0:
																quest(npc, owner);
															break;
															case 1:
																owner.setBusy(false);
																npc.unblock();
															break;
														}
													}
												});
											}
										});
									}	
								});		
							}	
						});		
					}	
				});		
			}
		});
	}
	
		
	private void quest(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Elena's a missionary and a healer", "Three weeks ago she managed to cross the ardougne wall", "No one's allowed to cross the wall in case they spread the plague", "but after hearing the scream of suffering she felt she had to help", "she said she'd be gone for a few days but we've heard nothing since"}) 
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) 
				{
					public void action() 
					{
						final String[] options107 = {"Tell me more about the plague", "Can I help find her?", "I'm sorry I have to go"};
						owner.setBusy(false);
						owner.sendMenu(options107);
						owner.setMenuHandler(new MenuHandler(options107)
						{
							public void handleReply(final int option, final String reply)
							{
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView())
								{
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) 
								{
									case 0:
										plagueInfo(npc, owner);
									break;
									case 1:
										questAccepted(npc, owner);
									break;
									case 2:
										owner.setBusy(false);
										npc.unblock();
									break;
								}
							}
						});
					}
				});
			}
		});	
	}
	
	
	private void questStage2(final Npc npc, final Player owner)
	{
		if(owner.getInventory().countId(766) == 0)
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"go get the gasmask from my wife", "you will need it to survive in west ardougne"}, true)
			{
				public void finished() 
				{
					owner.setBusy(false);
					npc.unblock();
				}
			});
		}
		else
		if(owner.getInventory().countId(766) > 0) 
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hi Edmond, I've got the gasmask now"}, true)
			{
				public void finished() 
				{
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"good stuff, now for the digging", "beneath are the ardougne sewers", "there you'll find access to west ardougne", "the problem is the soil is rock hard", "you'll need to pour on four buckets of water to soften it up", "I'll keep an eye out for the mourners"}) 
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
	}
	
	private void questStage3(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I've soaked the soil with water"}, true) 
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"That's great, it should be soft enough to dig through now"}) 
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
	
	private void questStage4(final Npc npc, final Player owner)
	{
		if (owner.getY() > 3412)
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Edmond, I can't get through to west ardougne", "there's an iron grill blocking my way", "I can't pull it off alone"}, true)
			{
				public void finished()
				{
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"If you get some rope you could tie it to the grill", "then we could both pull it from here"})
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
		else
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"I'll meet you down there"}, true)
			{
				public void finished() 
				{
					owner.setBusy(false);
					npc.unblock();
				}
			});
		}
	}
	
	
	private void questStage5(final Npc npc, final Player owner)
	{
		if (owner.getY() > 3412)
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I've tied the other end of this rope to the grill"}, true) 
			{
				public void finished() 
				{
					World.getDelayedEventHandler().add(new SingleEvent(owner, 2000) 
					{
						public void action() 
						{
							owner.sendMessage("Edmond gets a good grip of the rope");
							World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
							{
								public void action()
								{
									owner.sendMessage("You both pull");
									World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
									{
										public void action()
										{
											owner.sendMessage("You hear a clunk as you both fly backwards");
											owner.incQuestCompletionStage(Quests.PLAGUE_CITY);
											World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"that's done the job", "remember always wear the gasmask", "otherwise you'll die over there for certain", "and please bring my elena back safe and sound"}) 
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
							});
						}
					});
				}
			});
		}
		else
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"I'll meet you down there"}, true) 
			{
				public void finished() 
				{
					owner.setBusy(false);
					npc.unblock();
				}
			});
		}
	}
	
	private void questStageRest(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hey there", "Have you found my daughter yet?"}, true) 
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I'm still looking"}) 
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
	
	private void questStage13(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Thank you, thank you", "Elena beat you back by minutes", "Now I said I'd give you a reward"}, true) 
		{
			public void finished() 
			{
				owner.finishQuest(Quests.PLAGUE_CITY);
				owner.sendMessage("@gre@Well done you have completed the Plague City quest!");
				owner.sendMessage("@gre@You have been awarded 1 quest points!");
				owner.incQuestExp(14, 175 + 75 * owner.getMaxStat(14));
				owner.sendStat(14);
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"What can I give you as a reward I wonder?", "Here take this magic scroll", "I have little use for it, but it may help you"}) 
				{
					public void finished() 
					{
						owner.getInventory().add(752, 1);
						owner.sendInventory();
						owner.sendMessage("This story is to be continued");
						owner.setBusy(false);
						npc.unblock();
						Logger.log(new eventLog(owner.getUsernameHash(), owner.getAccount(), owner.getIP(), DataConversions.getTimeStamp(), "<strong>" + owner.getUsername() + "</strong>" + " has completed the <span class=\"recent_quest\">Plague City</span> quest!"));
					}
				});
			}
		});
	}
	
	private void finished(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hello again", "thank you for your help"})
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Don't mention it"}) 
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
	
	private void questStarted(final Npc npc, final Player owner) 
	{
		if(owner.getInventory().countId(765) == 0) 
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hello Edmond"}, true) 
			{
				public void finished() 
				{
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"have you got the dwellberries?"}) 
					{
						public void finished() 
						{
							World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"sorry I'm afraid not"}) 
							{
								public void finished() 
								{
									World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"You'll probably find them in mcgrubor's wood to the north"}) 
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
					});
				}
			});
		}
		else
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hello Edmond"}, true) 
			{
				public void finished() 
				{
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"have you got the dwellberries?"}) 
					{
						public void finished() 
						{
							World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Yes I have some here"}) 
							{
								public void finished() 
								{
									World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Take them to my wife Alrena"}) 
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
					});
				}
			});
		}
	}
	
	private void plagueInfo(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"The mourners can tell you more than me", "they're the only ones allowed to cross the border", "I do know the plague is a horrible way to go", "that's why elena felt she had to go help"}) 
		{
			public void finished()
			{
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) 
				{
					public void action() 
					{
						final String[] options107 = {"Can I help find her?", "I'm sorry I have to go"};
						owner.setBusy(false);
						owner.sendMenu(options107);
						owner.setMenuHandler(new MenuHandler(options107)
						{
							public void handleReply(final int option, final String reply)
							{
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView())
								{
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) 
								{
									case 0:
										questAccepted(npc, owner);
									break;
									case 1:
										owner.setBusy(false);
										npc.unblock();
									break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	private void questAccepted(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"really, would you?", "I've been working on a plan to get over the wall", "But I'm too old and tired to carry it through", "if you're going over the first thing you'll need is protection from the plague", "My wife made a special gasmask for elena", "with dwellberries rubbed into it", "Dwellberries help repel the virus", "We need some more though"})
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Where can I find these Dwellberries?"})
				{
					public void finished()
					{
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"The only place I know is mcgrubor's wood to the north"})
						{
							public void finished()
							{
								World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Ok I'll go get some"})
								{
									public void finished()
									{
										owner.addQuest(Quests.PLAGUE_CITY, 1);	
										owner.incQuestCompletionStage(Quests.PLAGUE_CITY);
										owner.setBusy(false);
										npc.unblock();
									}	
								});
							}	
						});		
					}	
				});		
			}
		});
	}

	
	
	
}