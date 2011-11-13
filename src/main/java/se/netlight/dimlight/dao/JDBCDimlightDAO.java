package se.netlight.dimlight.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class JDBCDimlightDAO implements IDimlightDAO {
	protected JdbcTemplate template;
	
	public void setDatasource(DataSource source)  {
		template = new JdbcTemplate(source);
		prepare(source);
	}

	protected abstract void prepare(DataSource source);
}
