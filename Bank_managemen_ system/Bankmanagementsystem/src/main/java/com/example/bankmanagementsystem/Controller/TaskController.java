package com.example.tasktrackersystem.Controller;

import com.example.tasktrackersystem.ApiResponse.ApiResponse;
import com.example.tasktrackersystem.Model.TaskModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    ArrayList<TaskModel> tasks=new ArrayList<>();

    @GetMapping("/display")
    public ArrayList<TaskModel> displayTasks(){
        return tasks;
    }

    @PostMapping("/create")
    public ApiResponse createTask(@RequestBody TaskModel task){
        String id= String.valueOf(tasks.size());
        task.setID(id);
        tasks.add(task);
        return new ApiResponse("Created","200");
    }


    @PutMapping("/update/{index}")
    public ApiResponse updateTask(@PathVariable int index, @RequestBody TaskModel task){
        if(index<tasks.size()) {
            String id = String.valueOf(index);
            task.setID(id);
            tasks.set(index, task);
            return new ApiResponse("Updated", "200");
        }
        return new ApiResponse("Can't update ", "400");

    }

    @DeleteMapping("/delete/{index}")
    public ApiResponse deleteTask(@PathVariable int index){

        if(index<tasks.size()) {
            tasks.remove(index);
            for (int i = index; i < tasks.size(); i++) {
                String id = String.valueOf(i);
                tasks.get(i).setID(id);
            }
            return new ApiResponse("Deleted", "200");
        }
        return new ApiResponse("Can't delete ", "400");

    }


    @PutMapping ("/changestatus/{index}")
    public ApiResponse changeStatus(@PathVariable int index){
        if (index<tasks.size()) {
            String status = tasks.get(index).getStatus();
            if (status.equals("done"))
                tasks.get(index).setStatus("not done");
            else
                tasks.get(index).setStatus("done");
            return new ApiResponse("Status changed ", "200");
        }
        return new ApiResponse("Can't change status ", "400");

    }


    @GetMapping ("/searchtask/{title}")
    public String searchTask(@PathVariable String title){
        for (TaskModel task:tasks) {
           if (task.getTitle().equals(title))
               return "Id= "+task.getID()+", Status= "+task.getStatus()+", Description= "+task.getDescription();
        }
        return "Not found";

    }



}
