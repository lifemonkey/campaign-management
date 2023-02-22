package campaign.service;

import campaign.domain.Campaign;
import campaign.domain.File;
import campaign.domain.Reward;
import campaign.repository.CampaignRepository;
import campaign.repository.FileRepository;
import campaign.repository.RewardRepository;
import campaign.service.dto.FileDTO;
import campaign.service.mapper.FileMapper;
import campaign.web.rest.vm.FileVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

//    @Value("${file.upload-dir}")
    private String fileUploadDir = "./files-storage";

    private final Path fileStorageLocation;

    private final FileRepository fileRepository;

    private final CampaignRepository campaignRepository;

    private final FileMapper fileMapper;
    private final RewardRepository rewardRepository;

    public FileService(FileRepository fileRepository, CampaignRepository campaignRepository, FileMapper fileMapper,
                       RewardRepository rewardRepository) {
        this.fileRepository = fileRepository;
        this.campaignRepository = campaignRepository;
        this.fileMapper = fileMapper;
        // init File directory use to store uploaded files
        this.fileStorageLocation = Paths.get(this.fileUploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.error("Could not create the directory where the uploaded files will be stored.");
        }
        this.rewardRepository = rewardRepository;
    }

    @Transactional(readOnly = true)
    public FileDTO getFileById(Long id) {
        return fileMapper.fileToFileDTO(fileRepository.findById(id).orElse(new File()));
    }

    @Transactional(readOnly = true)
    public Page<FileDTO> getAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable).map(FileDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<FileDTO> searchFiles(Pageable pageable, String search, Integer type) {
        if (search != null && type == null) {
            return fileRepository.findAllByNameContainingIgnoreCase(search, pageable).map(FileDTO::new);
        } else if (search == null && type != null) {
            return fileRepository.findAllByFileType(type, pageable).map(FileDTO::new);
        } else {
            return fileRepository.findAllByNameContainingIgnoreCaseAndFileType(search, type, pageable).map(FileDTO::new);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<FileDTO> uploadFiles(MultipartFile[] files) {
        return Arrays.asList(files)
            .stream().map(file -> uploadFile(file, "", null))
            .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO uploadFile(MultipartFile file, String description, Integer type) {

        // store file to server dir
        String fileUrl = ServiceUtils.isImageType(file.getOriginalFilename())
            ? ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/preview/")
                .path(storeFile(file))
                .toUriString()
            : "";

        // get file by fileName from db
        List<File> fileList = fileRepository.findByNameIgnoreCase(StringUtils.cleanPath(file.getOriginalFilename()));

        File toBeSaved = new File();
        if (fileList != null && fileList.size() > 0) {
            toBeSaved = fileList.get(0);
            toBeSaved.description(description).type(type);
        } else {
            toBeSaved.name(StringUtils.cleanPath(file.getOriginalFilename()))
                .description(description)
                .type(type)
                .url(fileUrl);
        }
        // save file and convert to DTO
        return fileMapper.fileToFileDTO(fileRepository.save(toBeSaved));
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                log.error("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            log.error("Could not store file " + fileName + ". Please try again!");
        }
        return null;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            String cleanPath = StringUtils.cleanPath(Paths.get(fileName).getFileName().toString());
            // fileUrl
            Path filePath = this.fileStorageLocation.resolve(cleanPath).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                log.warn("File not found " + filePath.getFileName());
            }
        } catch (MalformedURLException ex) {
            log.error("FileVM is invalid " + fileName, ex);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO updateFile(Long id, FileVM fileVM) {
        File file = fileMapper.fileVMToFile(fileVM);
        file.setId(id);

        fileRepository.save(file);
        return fileMapper.fileToFileDTO(file);
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO updateFileCampaign(Long id, Long campaignId) {
        Optional<File> fileOpt = fileRepository.findById(id);
        Optional<Campaign> campaignOpt = campaignRepository.findById(campaignId);

        if (fileOpt.isPresent() && campaignOpt.isPresent()) {
            File file = fileOpt.get();
            // link campaign to file
            file.setCampaign(campaignOpt.get());
            fileRepository.save(file);
            return fileMapper.fileToFileDTO(file);
        }

        return new FileDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public FileDTO updateFileReward(Long id, Long rewardId) {
        Optional<File> fileOpt = fileRepository.findById(id);
        Optional<Reward> rewardOpt = rewardRepository.findById(rewardId);

        if (fileOpt.isPresent() && rewardOpt.isPresent()) {
            File file = fileOpt.get();
            // link campaign to file
            file.setReward(rewardOpt.get());
            fileRepository.save(file);
            return fileMapper.fileToFileDTO(file);
        }

        return new FileDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<FileDTO> updateFilesReward(List<Long> ids, Long rewardId) {
        List<File> fileList = fileRepository.findAllById(ids);
        Optional<Reward> rewardOpt = rewardRepository.findById(rewardId);

        if (fileList != null && rewardOpt.isPresent()) {
            // link campaign to file
            fileList.stream().forEach(file -> file.setReward(rewardOpt.get()));
            fileRepository.saveAll(fileList);
            return fileMapper.fileToFileDTOs(fileList);
        }

        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Long id) {
        Optional<File> fileOpt = fileRepository.findById(id);
        if (fileOpt.isPresent()) {
            File file = fileOpt.get();
            file.setCampaign(null);
            fileRepository.save(file);
            fileRepository.delete(file);
        }
    }
}
