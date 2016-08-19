package pl.lodz.p.zzpj.domain.util;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

/**
 * Created by piotr on 19.08.16.
 */
public class FixedH2Dialect extends H2Dialect {
    public FixedH2Dialect() {
        super();
        registerColumnType(Types.FLOAT, "real");
    }
}
