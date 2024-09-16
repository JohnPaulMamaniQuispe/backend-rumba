package com.backendrumba.rumba_music.service;import com.backendrumba.rumba_music.model.Roles;import com.backendrumba.rumba_music.model.Song;import com.backendrumba.rumba_music.model.User;import com.backendrumba.rumba_music.repository.SongRepository;import com.backendrumba.rumba_music.repository.UserRepository;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.core.io.ByteArrayResource;import org.springframework.core.io.Resource;import org.springframework.stereotype.Service;import org.springframework.web.multipart.MultipartFile;import java.io.IOException;import java.nio.file.Files;import java.nio.file.Path;import java.nio.file.Paths;import java.time.LocalDateTime;import java.util.List;import java.util.Optional;@Servicepublic class SongService {    @Autowired    private SongRepository songRepository;    @Autowired    private UserService userService;    // Método para subir una canción (solo DJs o ADMIN)    public String saveSong(MultipartFile file, Long userId) throws IOException {        Optional<User> userOptional = userService.findById(userId);        if (userOptional.isEmpty()) {            throw new RuntimeException("Usuario no encontrado");        }        User user = userOptional.get();        if (user.getRole() != Roles.DJ && user.getRole() != Roles.ADMIN) {            throw new RuntimeException("Solo DJs o ADMIN pueden subir canciones.");        }        byte[] fileData = file.getBytes();        Song song = new Song();        song.setFileData(fileData);        song.setName(file.getOriginalFilename());        song.setUser(user);  // Asignar el usuario que sube la canción        songRepository.save(song);        return file.getOriginalFilename();    }    // Método para obtener el archivo de la canción por su ID desde el BLOB en la base de datos    public Resource getSongFile(Long id) throws IOException {        Optional<Song> songOptional = songRepository.findById(id);        if (songOptional.isPresent()) {            Song song = songOptional.get();            byte[] fileData = song.getFileData(); // Obtener los bytes desde el BLOB            if (fileData != null) {                return new ByteArrayResource(fileData);            } else {                throw new RuntimeException("Archivo no encontrado en la base de datos para la canción con ID: " + id);            }        } else {            throw new RuntimeException("Canción no encontrada con el ID: " + id);        }    }    // Método para obtener una canción por su ID    public Optional<Song> getSongById(Long id) {        return songRepository.findById(id);    }    // Método para obtener todas las canciones    public List<Song> getAllSongs() {        return songRepository.findAll();    }    // Método para listar las canciones subidas por un DJ o ADMIN    public List<Song> getSongsByDJOrAdmin(Long userId) {        Optional<User> userOptional = userService.findById(userId);        if (userOptional.isPresent()) {            User user = userOptional.get();            if (user.getRole() == Roles.DJ || user.getRole() == Roles.ADMIN) {                // Filtrar las canciones subidas por el usuario DJ o ADMIN                return songRepository.findByUser(user);            } else {                throw new RuntimeException("Solo DJs o ADMIN pueden listar sus canciones.");            }        } else {            throw new RuntimeException("Usuario no encontrado.");        }    }    // Método para eliminar una canción por su ID    public void deleteSong(Long id) throws IOException {        Optional<Song> songOptional = songRepository.findById(id);        if (songOptional.isPresent()) {            Song song = songOptional.get();            songRepository.deleteById(id);        } else {            throw new RuntimeException("Canción no encontrada con el ID: " + id);        }    }}