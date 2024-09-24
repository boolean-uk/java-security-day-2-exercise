package com.booleanuk.api.control;


import com.booleanuk.api.model.CD;
import com.booleanuk.api.model.ResponseObject;
import com.booleanuk.api.view.CDRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("cds")
public class CDController {

	private final CDRepository cdRepo;

	private HashMap<String, String> errorMessage;

	public CDController(CDRepository cdRepository) {
		this.cdRepo = cdRepository;
		this.errorMessage = new HashMap<>();
		errorMessage.put("message", "Failed");
	}

	@GetMapping
	public ResponseEntity<ResponseObject<List<CD>>> getAll() {
		return new ResponseEntity<>(new ResponseObject<>("Success", cdRepo.findAll()), HttpStatus.OK);
	}

	public void checkIfValidCD(CD cd) {
		try {
			if (cd.getName() == null || cd.getName().isEmpty()) {
				throw new Exception("Invalid CD name");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body: " + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<ResponseObject<?>> postOne(@RequestBody CD cd) {
		try {
			checkIfValidCD(cd);
		} catch (ResponseStatusException e) {
			return cdInvalidRequest();
		}

		return new ResponseEntity<>(new ResponseObject<>("Success", cdRepo.save(cd)), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseObject<?>> putOne(@PathVariable int id, @RequestBody CD cd) {
		CD cdToUpdate;
		try {
			cdToUpdate = cdRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

			checkIfValidCD(cd);
		} catch (ResponseStatusException e) {
			return cdNotFound();
		}

		cdToUpdate.setName(cd.getName());

		return new ResponseEntity<>(new ResponseObject<>("Success", cdRepo.save(cdToUpdate)), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseObject<?>> deleteCD(@PathVariable int id) {
		CD delCD;
		try {
			delCD = cdRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		} catch (ResponseStatusException e) {
			return cdNotFound();
		}

		cdRepo.delete(delCD);
		return new ResponseEntity<>(new ResponseObject<>("Success", delCD), HttpStatus.OK);
	}

	public ResponseEntity<ResponseObject<?>> cdNotFound() {
		return new ResponseEntity<>(new ResponseObject<>("Failed: CD not found"), HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseObject<?>> cdInvalidRequest() {
		return new ResponseEntity<>(new ResponseObject<>("Failed: Invalid request body"), HttpStatus.BAD_REQUEST);
	}
}
