package campaign.service.mapper;

import campaign.domain.File;
import campaign.service.dto.FileDTO;
import campaign.web.rest.vm.FileVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileMapper {

    public FileDTO fileToFileDTO(File file) {
        return new FileDTO(file);
    }

    public List<FileDTO> fileToFileDTOs(List<File> fileList) {
        return fileList.stream()
            .filter(Objects::nonNull)
            .map(this::fileToFileDTO)
            .collect(Collectors.toList());
    }

    public File fileDTOToFiles(FileDTO fileDTO) {
        if (fileDTO == null) {
            return null;
        } else {
            File file = new File();
            file.setId(fileDTO.getId());
            file.setName(fileDTO.getName());
            file.setDescription(fileDTO.getDescription());
            file.setFileType(fileDTO.getFileType());
            file.setImageUrl(fileDTO.getImageUrl());

            return file;
        }
    }

    public List<File> fileDTOToFiles(List<FileDTO> fileDTOS) {
        return fileDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::fileDTOToFiles)
            .collect(Collectors.toList());
    }

    public File fileVMToFile(FileVM fileVM) {
        if (fileVM == null) {
            return null;
        } else {
            File file = new File();
            if (fileVM.getId() != null) {
                file.setId(fileVM.getId());
            }
            file.setName(fileVM.getName());
            file.setDescription(fileVM.getDescription());
            file.setFileType(fileVM.getFileType());
            file.setImageUrl(fileVM.getImageUrl());

            return file;
        }
    }

    public List<File> fileVMToFiles(List<FileVM> fileVMs) {
        return fileVMs.stream()
            .filter(Objects::nonNull)
            .map(this::fileVMToFile)
            .collect(Collectors.toList());
    }
}
