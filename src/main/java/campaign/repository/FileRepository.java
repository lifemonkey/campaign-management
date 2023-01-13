package campaign.repository;

import campaign.domain.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Page<File> findAllByNameContaining(String name, Pageable pageable);

    Page<File> findAllByFileType(Integer fileType, Pageable pageable);

    Page<File> findAllByNameContainingAndFileType(String name, Integer fileType, Pageable pageable);

    Optional<File> findByName(String name);
}
