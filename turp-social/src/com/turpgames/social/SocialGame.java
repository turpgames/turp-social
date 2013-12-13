package com.turpgames.social;

import com.turpgames.framework.v0.forms.xml.Control;
import com.turpgames.framework.v0.forms.xml.IControlActionHandler;
import com.turpgames.framework.v0.forms.xml.IControlActionHandlerFactory;
import com.turpgames.framework.v0.impl.BaseGame;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.social.Player;
import com.turpgames.framework.v0.social.SocialFeed;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;

public class SocialGame extends BaseGame {
	private ISocializer facebook;

	@Override
	public void init() {
		Game.getResourceManager().beginLoading();
		while (Game.getResourceManager().isLoading())
			Utils.threadSleep(100);

		facebook = Game.getSocializer("facebook");

		Game.getActionHandlerFactory().setSuccessor(createSocailActionHandlerFactory());

		super.init();
	}

	private IControlActionHandlerFactory createSocailActionHandlerFactory() {
		return new IControlActionHandlerFactory() {
			@Override
			public void setSuccessor(IControlActionHandlerFactory successor) {
			}

			@Override
			public IControlActionHandler create(Control control, String action) {
				return createActionHandler(action);
			}
		};
	}

	private IControlActionHandler createActionHandler(String action) {
		if ("login-facebook".equals(action)) {
			return new IControlActionHandler() {
				@Override
				public void handle(Control control) {
					facebookLogin();
				}
			};
		}
		else if ("post-facebook".equals(action)) {
			return new IControlActionHandler() {
				@Override
				public void handle(Control control) {
					facebookPostFeed();
				}
			};
		}
		return IControlActionHandler.NULL;
	}

	private void facebookLogin() {
		facebook.login(new ICallback() {
			@Override
			public void onSuccess() {
				Player player = facebook.getPlayer();
				System.out.println("social-id:  " + player.getSocialId());
				System.out.println("email:      " + player.getEmail());
				System.out.println("name:       " + player.getName());
				System.out.println("avatar-url: " + player.getAvatarUrl());
			}

			@Override
			public void onFail(Throwable t) {
				if (t != null)
					t.printStackTrace();
			}
		});
	}

	private void facebookPostFeed() {
		facebook.postFeed(
				SocialFeed.newBuilder()
						.setTitle("Title")
						.setSubtitle("Subtitle")
						.setHref("https://play.google.com/store/apps/details?id=com.turpgames.ichigu")
						.setMessage("Message")
						.setImageUrl("http://www.mbmt.net/homepage/github.png")
						.build(),
				new ICallback() {

					@Override
					public void onSuccess() {
						System.out.println("sent");
					}

					@Override
					public void onFail(Throwable t) {
						if (t != null)
							t.printStackTrace();
					}
				});
	}
}
