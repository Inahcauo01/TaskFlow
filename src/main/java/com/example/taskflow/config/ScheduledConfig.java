package com.example.taskflow.config;

import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.taskflow.domain.enums.TaskStatus.DONE;
import static com.example.taskflow.domain.enums.TaskStatus.UNDONE;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledConfig {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // s(0-59) min(0-59) h(0-23) j(1-31) m(1-12) j(0-7)
    @Scheduled(cron = "0 0 0 * * ?") // execute every day at midnight
    public void updateScoreJetons(){
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setJetons(2));
        userRepository.saveAll(users);

        List<Task> tasks = taskRepository.findAll();
        tasks.forEach(task -> {
            if (task.getEndDate().isBefore(LocalDateTime.now()) && !task.getStatus().equals(DONE)){
                task.setStatus(UNDONE);
            }
        });
        taskRepository.saveAll(tasks);
    }
}
