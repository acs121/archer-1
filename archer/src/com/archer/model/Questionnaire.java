package com.archer.model;

public class Questionnaire {
		private String username;
		private String questionnaire_id;
		private String questionnaire_name;
		private String questionnaire_content;
		private String writeURL;
		private int version;
		private int status;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getQuestionnaire_id() {
			return questionnaire_id;
		}
		public void setQuestionnaire_id(String questionnaire_id) {
			this.questionnaire_id = questionnaire_id;
		}
		public String getQuestionnaire_name() {
			return questionnaire_name;
		}
		public void setQuestionnaire_name(String questionnaire_name) {
			this.questionnaire_name = questionnaire_name;
		}
		public String getQuestionnaire_content() {
			return questionnaire_content;
		}
		public void setQuestionnaire_content(String questionnaire_content) {
			this.questionnaire_content = questionnaire_content;
		}
		public String getWriteURL() {
			return writeURL;
		}
		public void setWriteURL(String writeURL) {
			this.writeURL = writeURL;
		}
		public int getVersion() {
			return version;
		}
		public void setVersion(int version) {
			this.version = version;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
}
