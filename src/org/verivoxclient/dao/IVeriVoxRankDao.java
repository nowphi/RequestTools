/**
 * 
 */
package org.verivoxclient.dao;

import java.util.List;

import org.verivoxclient.controller.VeriVoxOffersRequestThread;

/**
 * @author user
 *
 */
public interface IVeriVoxRankDao {
	public List<VeriVoxOffersRequestThread> requestPostCodeList(int modus, String provider);
}
