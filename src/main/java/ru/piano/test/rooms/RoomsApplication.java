package ru.piano.test.rooms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.piano.test.rooms.model.Door;
import ru.piano.test.rooms.model.User;
import ru.piano.test.rooms.repository.DoorRepository;
import ru.piano.test.rooms.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootApplication
public class RoomsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomsApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(DoorRepository doorRepository, @Value("${doors.count:0}") Long doorsCount,
									  UserRepository userRepository, @Value("${users.count:0}") Long usersCount) {
		return (args) -> {
			List<Door> doors = LongStream.range(0, doorsCount)
					.mapToObj(Door::new)
					.collect(Collectors.toList());
			doorRepository.saveAllAndFlush(doors);

			List<User> users = LongStream.range(0, usersCount)
					.mapToObj(User::new)
					.collect(Collectors.toList());
			userRepository.saveAllAndFlush(users);
		};
	}
}
