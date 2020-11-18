package com.example.springbacth.writer;

import com.example.springbacth.model.Users;
import com.example.springbacth.repository.UserRepository;
import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBwriter implements ItemWriter<Users> {

  @Autowired
  private UserRepository userRepository;

  @Override
  public void write(List<? extends Users> list) throws Exception {
    userRepository.saveAll(list);
  }
}
