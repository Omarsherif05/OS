package process;

import process.*;
import Memory.SharedMemory;
import Queues.readyQueue;

import java.io.*;
import java.util.*;

import static Program_parser.programParser.parseInstructionFile;

public class Main {

    public static void main(String[] args) {
        // File path to read processes from
        String filePath = "Program_2.txt";  // Update this path to your text file location

        // Initialize shared memory and ready queue
        SharedMemory sharedMemory = new SharedMemory();
        readyQueue queue = new readyQueue();

            // Read and parse the text file to create processes
            parseInstructionFile(filePath);

            // Initialize the master core with the shared memory and ready queue
            MasterCore masterCore = new MasterCore(sharedMemory, queue);

            // Start execution of processes
            masterCore.startExecution();

    }
}