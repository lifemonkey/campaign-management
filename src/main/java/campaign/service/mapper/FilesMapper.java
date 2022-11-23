package campaign.service.mapper;

import campaign.domain.Files;
import campaign.service.dto.FilesDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FilesMapper {

    public FilesDTO filesToFilesDTO(Files file) {
        return new FilesDTO(file);
    }

    public List<FilesDTO> filesToFilesDTOs(List<Files> filesList) {
        return filesList.stream()
            .filter(Objects::nonNull)
            .map(this::filesToFilesDTO)
            .collect(Collectors.toList());
    }

    public Files filesDTOToFile(FilesDTO filesDTO) {
        if (filesDTO == null) {
            return null;
        } else {
            Files file = new Files();
            file.setId(filesDTO.getId());
            file.setName(filesDTO.getName());
            file.setDescription(filesDTO.getDescription());
            file.setFileType(filesDTO.getFileType());
            file.setImageUrl(filesDTO.getImageUrl());
            file.setImageBlob(filesDTO.getImageBlob());
            file.setCreatedBy(filesDTO.getCreatedBy());
            file.setCreatedDate(filesDTO.getCreatedDate());
            file.setLastModifiedBy(filesDTO.getLastModifiedBy());
            file.setLastModifiedDate(filesDTO.getLastModifiedDate());

            return file;
        }
    }

    public List<Files> filesDTOToFiles(List<FilesDTO> filesDTOs) {
        return filesDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::filesDTOToFile)
            .collect(Collectors.toList());
    }
}
