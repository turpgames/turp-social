package com.turpgames.social;

import com.turpgames.framework.v0.forms.xml.Control;
import com.turpgames.framework.v0.forms.xml.IControlActionHandler;
import com.turpgames.framework.v0.forms.xml.IControlActionHandlerFactory;
import com.turpgames.framework.v0.impl.BaseGame;
import com.turpgames.framework.v0.social.ILoginCallback;
import com.turpgames.framework.v0.social.IPostCallback;
import com.turpgames.framework.v0.social.Player;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;

public class SocialGame extends BaseGame {
	@Override
	public void init() {
		Game.getResourceManager().beginLoading();
		while (Game.getResourceManager().isLoading())
			Utils.threadSleep(100);

		Game.getActionHandlerFactory().setSuccessor(
				new IControlActionHandlerFactory() {
					@Override
					public void setSuccessor(
							IControlActionHandlerFactory successor) {

					}

					@Override
					public IControlActionHandler create(Control control, String action) {
						if ("login-facebook".equals(action)) {
							return new IControlActionHandler() {
								@Override
								public void handle(Control control) {
									Game.getSocializer().createAuth("facebook").login(new ILoginCallback() {										
										@Override
										public void onLoginSuccess(Player player) {
											System.out.println("social-id:  " + player.getSocialId());
											System.out.println("email:      " + player.getEmail());
											System.out.println("name:       " + player.getName());
											System.out.println("avatar-url: " + player.getAvatarUrl());
										}
										
										@Override
										public void onLoginFail() {
											
										}
									});
								}
							};
						}
						else if ("post-facebook".equals(action)) {
							return new IControlActionHandler() {
								@Override
								public void handle(Control control) {
									Game.getSocializer().createAuth("facebook").postText("Sent from my iPhone.", new IPostCallback() {
										
										@Override
										public void postSent() {
											System.out.println("sent");
										}
										
										@Override
										public void postFailed() {
											System.out.println("failed");
										}
									});
								}
							};
						}
						return IControlActionHandler.NULL;
					}
				});

		super.init();
	}
}
