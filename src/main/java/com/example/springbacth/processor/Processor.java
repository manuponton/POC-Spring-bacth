package com.example.springbacth.processor;

import com.example.springbacth.model.Users;
import java.util.Date;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Users, Users> {

  @Override
  public Users process(Users users) throws Exception {
    users.setTimestamp(new Date());
    return users;
  }
}
