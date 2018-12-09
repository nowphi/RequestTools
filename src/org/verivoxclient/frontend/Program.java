package org.verivoxclient.frontend;

import java.awt.EventQueue;

public final class Program {
		// TODO Auto-generated constructor stub
		public static void main(String[] args) {
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						FrontendVeriVox frame = new FrontendVeriVox();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
