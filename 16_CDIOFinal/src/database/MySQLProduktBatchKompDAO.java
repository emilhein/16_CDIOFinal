package daoimpl01917;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;

import daointerfaces01917.DALException;
import daointerfaces01917.ProduktBatchKompDAO;

import dto01917.ProduktBatchKompDTO;

public class MySQLProduktBatchKompDAO implements ProduktBatchKompDAO {

	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {
	ResultSet rs = Connector.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id = " + pbId);
    try {
    	if (!rs.first()) throw new DALException("Produktet " + pbId + " findes ikke"); 
    	return new ProduktBatchKompDTO (rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5));
    }
    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId)  throws DALException {
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatchkomponent");
		try {
			while (rs.next()) {
			list.add(new ProduktBatchKompDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;	
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList()
			throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void createProduktBatchKomp(ProduktBatchKompDTO pbk)throws DALException {
		Connector.doUpdate(
				"INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, opr_id) VALUES " +
				"(" + pbk.getPbId() + ", '" + pbk.getRbId() + "', '" + pbk.getTara()+ "', '"
						+ pbk.getNetto()+ "', '" + pbk.getOprId()+"')"
			);
		
	}

	
	public void updateProduktBatchKomp(ProduktBatchKompDTO pbk)throws DALException {
		Connector.doUpdate("UPDATE produktbatchkomponent SET  pb_id = '"
				+ pbk.getPbId() + "', rb_id =  '" + pbk.getRbId()
				+ "', tara = '" + pbk.getTara() + "', netto = '"
				+ pbk.getNetto() + "', opr_id = '" + pbk.getOprId()
				+ "' WHERE pb_id = " + pbk.getPbId());

	}

}
