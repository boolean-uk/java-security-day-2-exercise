package com.booleanuk.api.control;

import com.booleanuk.api.model.DVD;
import com.booleanuk.api.model.ResponseObject;
import com.booleanuk.api.view.DVDRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("dvds")
public class DVDController {

	private final DVDRepository dvdRepo;

	private HashMap<String, String> errorMessage;

	public DVDController(DVDRepository dvdRepository) {
		this.dvdRepo = dvdRepository;
		this.errorMessage = new HashMap<>();
		errorMessage.put("message", "Failed");
	}

	@GetMapping
	public ResponseEntity<ResponseObject<List<DVD>>> getAll() {
		return new ResponseEntity<>(new ResponseObject<>("Success", dvdRepo.findAll()), HttpStatus.OK);
	}

	public void checkIfValidDVD(DVD dvd) {
		try {
			if (dvd.getName() == null || dvd.getName().isEmpty()) {
				throw new Exception("Invalid DVD name");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body: " + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<ResponseObject<?>> postOne(@RequestBody DVD dvd) {
		try {
			checkIfValidDVD(dvd);
		} catch (ResponseStatusException e) {
			return dvdInvalidRequest();
		}

		return new ResponseEntity<>(new ResponseObject<>("Success", dvdRepo.save(dvd)), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseObject<?>> putOne(@PathVariable int id, @RequestBody DVD dvd) {
		DVD dvdToUpdate;
		try {
			dvdToUpdate = dvdRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

			checkIfValidDVD(dvd);
		} catch (ResponseStatusException e) {
			return dvdNotFound();
		}

		dvdToUpdate.setName(dvd.getName());

		return new ResponseEntity<>(new ResponseObject<>("Success", dvdRepo.save(dvdToUpdate)), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseObject<?>> deleteDVD(@PathVariable int id) {
		DVD delDVD;
		try {
			delDVD = dvdRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		} catch (ResponseStatusException e) {
			return dvdNotFound();
		}

		dvdRepo.delete(delDVD);
		return new ResponseEntity<>(new ResponseObject<>("Success", delDVD), HttpStatus.OK);
	}

	public ResponseEntity<ResponseObject<?>> dvdNotFound() {
		return new ResponseEntity<>(new ResponseObject<>("Failed: DVD not found"), HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseObject<?>> dvdInvalidRequest() {
		return new ResponseEntity<>(new ResponseObject<>("Failed: Invalid request body"), HttpStatus.BAD_REQUEST);
	}
}
