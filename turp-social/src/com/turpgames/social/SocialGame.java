package com.turpgames.social;

import com.turpgames.framework.v0.forms.xml.Control;
import com.turpgames.framework.v0.forms.xml.IControlActionHandler;
import com.turpgames.framework.v0.forms.xml.IControlActionHandlerFactory;
import com.turpgames.framework.v0.impl.BaseGame;
import com.turpgames.framework.v0.social.ILoginCallback;
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
					public IControlActionHandler create(Control control,
							String action) {
						if ("login-facebook".equals(action)) {
							return new IControlActionHandler() {
								@Override
								public void handle(Control control) {
									Game.getSocializer().createAuth("facebook").login(new ILoginCallback() {										
										@Override
										public void onLoginSuccess(Player player) {
											
										}
										
										@Override
										public void onLoginFail() {
											
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
