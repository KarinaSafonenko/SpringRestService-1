package com.vpaveldm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class LocalNetworkController {

    public LocalNetworkController() {
    }

    private LocalNetworkRepository repository = null;

    @Autowired
    public LocalNetworkController(LocalNetworkRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<String>("Hello, world!", HttpStatus.OK);
    }

    @RequestMapping(value = "changeLocalNetwork", method = RequestMethod.PUT)
    public ResponseEntity<Void> changeLocalNetwork(@RequestBody LocalNetwork network) {
        Optional<LocalNetwork> byId = repository.findById(network.getId());
        if (byId.isPresent()) {
            LocalNetwork oldNetwork = byId.get();
            repository.delete(oldNetwork);
            repository.save(network);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "removeLocalNetwork/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeLocalNetwork(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "addLocalNetwork", method = RequestMethod.POST)
    public ResponseEntity<Void> addLocalNetwork(@RequestBody LocalNetwork network) {
        repository.save(network);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "getLocalNetwork", method = RequestMethod.GET)
    public ResponseEntity<LocalNetwork[]> getLocalNetworks() {
        Iterable<LocalNetwork> networksIterator = repository.findAll();
        List<LocalNetwork> networks = new ArrayList<>();
        networksIterator.forEach(networks::add);
        return new ResponseEntity<>(networks.toArray(new LocalNetwork[0]), HttpStatus.OK);
    }

}
