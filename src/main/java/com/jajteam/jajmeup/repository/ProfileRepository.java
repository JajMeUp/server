package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.domain.QProfile;
import com.querydsl.core.types.EntityPath;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.util.List;

public class ProfileRepository extends AbstractRepository<Long,Profile> {

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public EntityPath<Profile> getEntityPath() {
        return QProfile.profile;
    }

    public List<Profile> findProfileByNameSearch(String search, Long searcherID) {
        String sql = "SELECT * FROM profile WHERE id != " + searcherID + " AND id NOT IN " +
                "(SELECT idTarget FROM friendship WHERE idRequester = " + String.valueOf(searcherID) + " UNION " +
                "SELECT idRequester FROM friendship WHERE idTarget = " + String.valueOf(searcherID) + ")";
        if (!search.isEmpty()) {
            sql = sql.concat(" AND LOWER(display_name) LIKE LOWER('%" + search + "%')");
        }
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Profile.class));
    }
}
