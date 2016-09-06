package pl.lodz.p.zzpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.lodz.p.zzpj.domain.util.Search;

import java.util.List;

/**
 * Created by piotr on 05.09.16.
 */
public interface SearchRepository extends JpaRepository<Search, Long> {
    @Query("from Search where userId = :id")
    List<Search> getUserSearchHistory(@Param("id") int id);
}
