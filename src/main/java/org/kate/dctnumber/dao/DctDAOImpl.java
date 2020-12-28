package org.kate.dctnumber.dao;

import org.kate.dctnumber.model.Dct;
import javax.ejb.Stateless;

@Stateless
public class DctDAOImpl extends GenericDAOImpl<Dct, Long>
    implements DctDAO {

    public DctDAOImpl() {
        super(Dct.class);
    }
}
