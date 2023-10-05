import java.util.*;

class Student {
	private String id;
	private String answer;
	
	// Each student has an associated ID and answer
	public Student(String id, String answer) {
		this.id = id;
		this.answer = answer;
	}
	
	public String getId() {
		return id;
	}

	public String getAnswer() {
		return answer;
	}		
}

enum QuestionType {
	MULTIPLE,
	SINGLE
}

class Question {
	private String questionText;
	private QuestionType questionType;
	private List<String> candidateAnswers;
	private char answer;
	
	// Set the question and answer
	public Question(String questionText, QuestionType questionType, List<String> candidateAnswers) {
		this.questionText = questionText;
		this.questionType = questionType;
		this.candidateAnswers = candidateAnswers;
		// Create random answer to problem
		Random rand = new Random();
        int randNum = rand.nextInt(4);
        char answer = (char) ('A' + randNum);
        this.answer = answer;

	}
	
	public char getAnswer() {
		return answer;
	}

	public String getQuestionText() {
		return questionText;
	}
	
	public QuestionType getQuestionType() {
		return questionType;
	}

	public List<String> getCandidateAnswers() {
		return candidateAnswers;
	}
}

class VotingService {
	private Question question;
	private Map<String, Integer> answerCounts;
	
	// Map the question to the answer
	public VotingService(Question question) {
		this.question = question;
		this.answerCounts = new HashMap<>();
		for(String answer : question.getCandidateAnswers()) {
			answerCounts.put(answer, 0);
		}
	}
	
	// Check to see if the answer is valid
	public boolean isValidAnswer(String answer) {
		return question.getCandidateAnswers().contains(answer);
	}
	
	// Submit the answer to question
	public void submitAnswer(Student student) {
		if (student != null && isValidAnswer(student.getAnswer()))
			answerCounts.put(student.getAnswer(), answerCounts.get(student.getAnswer()) + 1);
	}
	
	// Display total results of the poll
	public void display() {
		System.out.println("Voting results");
		for(Map.Entry<String, Integer> entry : answerCounts.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}

class SimulationDriver {

	public static void main(String[] args) {
		// Create question type and configure answers
		System.out.println("Vote on an answer: (A) (B) (C) (D)");
		List<String> candidateAnswers = Arrays.asList("A", "B", "C", "D");
		// Randomly choose multiple or single choice question
		Random rand = new Random();
		int choice = rand.nextInt(2);
		QuestionType questionType;
		if(choice == 1)
			questionType = QuestionType.MULTIPLE;
		else
			questionType = QuestionType.SINGLE;
		Question question = new Question("Answer: ", questionType, candidateAnswers);
		
		// Configure question for iVote Service
		VotingService votingService = new VotingService(question);
		
		// Randomly generate a random number of students from 20 - 30
		int numStudents = rand.nextInt(30 + 1) + 20;
		
		// Generate random answers for each student
		String studentId;
		for(int i = 0; i < numStudents; i++) {
			if(i < 10)
				studentId = "000" + (i + 1);
			else
				studentId = "00" + (i + 1);
			String randAnswer = candidateAnswers.get(rand.nextInt(candidateAnswers.size()));
			Student student = new Student(studentId, randAnswer);
			// Submit all answers to iVoteservice
			votingService.submitAnswer(student);
		}
		
		// call Voting Service output to display result
		votingService.display();
		printStats(numStudents, questionType, question.getAnswer());
	}
	
	public static void printStats(int numStudents, QuestionType questionType, char answer) {
		System.out.println("Number of students: " + numStudents);
		System.out.println("Question Type: " + questionType);
		System.out.println("Correct answer: " + answer);
	}
}
