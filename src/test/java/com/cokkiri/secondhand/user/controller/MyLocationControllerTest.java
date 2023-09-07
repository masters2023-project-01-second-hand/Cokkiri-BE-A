package com.cokkiri.secondhand.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cokkiri.secondhand.ApiTest;
import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.list.DuplicateMyLocationException;
import com.cokkiri.secondhand.global.exception.list.LimitExceededMyLocationException;
import com.cokkiri.secondhand.global.exception.list.MyLocationDeletionNotAllowedException;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
import com.cokkiri.secondhand.global.exception.list.NotFoundMyLocationException;
import com.cokkiri.secondhand.user.dto.request.AddMyLocationRequest;
import com.cokkiri.secondhand.user.dto.response.MyLocationListResponse;
import com.cokkiri.secondhand.user.dto.response.MyLocationResponse;
import com.cokkiri.secondhand.user.entity.UserType;
import com.cokkiri.secondhand.user.service.MyLocationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApiTest
class MyLocationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MyLocationService myLocationService;

	private final UserInfoForJwt userInfoForJwt = UserInfoForJwt.generateUserInfo(1L, UserType.GENERAL);

	@DisplayName("[내 동네 목록 불러오기]")
	@Nested
	class GetMyLocations {

		@DisplayName("내 동네 목록을 불러올 수 있다.")
		@WithMockUser
		@Test
		void getMyLocations() throws Exception {

			List<MyLocationResponse> locations = List.of();
			MyLocationListResponse result = new MyLocationListResponse(locations);

			when(myLocationService.getMyLocationByUserId(userInfoForJwt))
				.thenReturn(result);

			mockMvc.perform(
				get("/api/users/locations")
					.requestAttr("userInfoForJwt", userInfoForJwt)
					.characterEncoding("utf-8")
			).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.locations").isArray());
		}
	}

	@DisplayName("[내 동네 목록을 추가]")
	@Nested
	class AddMyLocation {

		private final Long locationId = 1L;
		private final String locationName = "서울 특별시 강남구 역삼1동";
		private final AddMyLocationRequest request = new AddMyLocationRequest(locationId);

		@DisplayName("내 동네 목록을 추가할 수 있다.")
		@Test
		void addMyLocationSuccess() throws Exception {

			MyLocationResponse result = new MyLocationResponse(locationId, locationName, false);

			when(myLocationService.addMyLocation(
					any(AddMyLocationRequest.class),
					any(UserInfoForJwt.class)))
				.thenReturn(result);

			mockMvc.perform(
				post("/api/users/locations")
					.requestAttr("userInfoForJwt", userInfoForJwt)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("utf-8")
			).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.name").isString())
				.andExpect(jsonPath("$.isSelected").isBoolean());
		}

		@DisplayName("내 동네 목록이 3개일 경우 더이상 추가할 수 없다. 409 Conflict 에러를 반환한다.")
		@Test
		void addMyLocationMoreThanThree() throws Exception {

			CustomException exception = new LimitExceededMyLocationException();

			when(myLocationService.addMyLocation(
				any(AddMyLocationRequest.class),
				any(UserInfoForJwt.class)))
				.thenThrow(exception);

			mockMvc.perform(
					post("/api/users/locations")
						.requestAttr("userInfoForJwt", userInfoForJwt)
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
				).andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value(exception.getMessage()));
		}

		@DisplayName("이미 추가된 동네는 내 동네 목록에 추가할 수 없다. 409 Conflict 에러를 반환한다.")
		@Test
		void addMyLocationDuplicated() throws Exception {

			CustomException exception = new DuplicateMyLocationException(locationName);

			when(myLocationService.addMyLocation(
				any(AddMyLocationRequest.class),
				any(UserInfoForJwt.class)))
				.thenThrow(exception);

			mockMvc.perform(
					post("/api/users/locations")
						.requestAttr("userInfoForJwt", userInfoForJwt)
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
				).andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value(exception.getMessage()));
		}

		@DisplayName("존재하지 않는 지역을 추가하면 404 Not Found 에러를 반환한다.")
		@Test
		void addMyLocationFailure() throws Exception {

			CustomException exception = new NotFoundLocationException(locationId);

			when(myLocationService.addMyLocation(
				any(AddMyLocationRequest.class),
				any(UserInfoForJwt.class)))
				.thenThrow(exception);

			mockMvc.perform(
					post("/api/users/locations")
						.requestAttr("userInfoForJwt", userInfoForJwt)
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
				).andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value(exception.getMessage()));
		}
	}

	@DisplayName("[내 동네 선택하기]")
	@Nested
	class ChooseMyLocation {

		private final Long myLocationId =  1L;

		@DisplayName("내 동네 목록 중 하나의 동네를 지정할 수 있다")
		@Test
		void chooseMyLocationSuccess() throws Exception {

			mockMvc.perform(
				patch("/api/users/locations/" + myLocationId)
					.requestAttr("userInfoForJwt", userInfoForJwt)
			).andDo(print())
				.andExpect(status().isOk());
		}

		@DisplayName("이미 선택된 동네를 지정하면 아무 변화가 없다.")
		@Test
		void chooseAlreadyChosenMyLocation() throws Exception {

			mockMvc.perform(
					patch("/api/users/locations/" + myLocationId)
						.requestAttr("userInfoForJwt", userInfoForJwt)
				).andDo(print())
				.andExpect(status().isOk());
		}

		@DisplayName("내 동네 목록에 없는 동네를 지정하면 404 Not Found 에러를 반환한다.")
		@Test
		void chooseMyLocationFailure() throws Exception {

			CustomException exception = new NotFoundMyLocationException(myLocationId);

			doThrow(exception)
				.when(myLocationService)
				.chooseMyLocation(
					anyLong(), any(UserInfoForJwt.class));

			mockMvc.perform(
					patch("/api/users/locations/" + myLocationId)
						.requestAttr("userInfoForJwt", userInfoForJwt)
				).andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value(exception.getMessage()));
		}
	}

	@DisplayName("[내 동네 삭제하기]")
	@Nested
	class RemoveMyLocation {

		private final Long myLocationId = 1L;

		@DisplayName("내 동네 목록 중 하나의 동네를 삭제할 수 있다")
		@Test
		void removeMyLocationSuccess() throws Exception {

			mockMvc.perform(
				delete("/api/users/locations/" + myLocationId)
					.requestAttr("userInfoForJwt", userInfoForJwt)
			).andDo(print())
				.andExpect(status().isNoContent());
		}

		@DisplayName("내 동네 목록이 하나일 경우 삭제할 수 없다. 400 Bad Request 에러를 반환한다.")
		@Test
		void removeMyLocationLessOne() throws Exception {

			CustomException exception = new MyLocationDeletionNotAllowedException();

			doThrow(exception)
				.when(myLocationService)
				.removeMyLocation(
					anyLong(), any(UserInfoForJwt.class));

			mockMvc.perform(
				delete("/api/users/locations/" + myLocationId)
					.requestAttr("userInfoForJwt", userInfoForJwt)
			).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(exception.getMessage()));
		}

		@DisplayName("이미 선택된 동네를 삭제하면 4xx에러를 반환한다.")
		@Test
		void removeChosenMyLocation() throws Exception {

			CustomException exception = new MyLocationDeletionNotAllowedException();

			doThrow(exception)
				.when(myLocationService)
				.removeMyLocation(
					anyLong(), any(UserInfoForJwt.class));

			mockMvc.perform(
					delete("/api/users/locations/" + myLocationId)
						.requestAttr("userInfoForJwt", userInfoForJwt)
				).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(exception.getMessage()));
		}

		@DisplayName("내 동네 목록에 없는 동네를 삭제하면 404 Not Found 에러를 반환한다.")
		@Test
		void removeMyLocationFailure() throws Exception {

			CustomException exception = new NotFoundMyLocationException(myLocationId);

			doThrow(exception)
				.when(myLocationService)
				.removeMyLocation(
					anyLong(), any(UserInfoForJwt.class));

			mockMvc.perform(
					delete("/api/users/locations/" + myLocationId)
						.requestAttr("userInfoForJwt", userInfoForJwt)
				).andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value(exception.getMessage()));
		}
	}
}