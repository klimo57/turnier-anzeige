package com.klimo.misc.util;

import java.util.Comparator;
import java.util.function.Function;

import com.klimo.misc.domain.AbstractParticipant;
import com.klimo.misc.domain.Player;
import com.klimo.misc.domain.Team;
import com.klimo.misc.dto.ParticipantDTO;

public interface Functions {

	public static final Function<AbstractParticipant, ParticipantDTO> PLAYER_DTO_MAPPER = (p) -> new ParticipantDTO((Player)p);
	public static final Function<AbstractParticipant, ParticipantDTO> TEAM_DTO_MAPPER = (p) -> new ParticipantDTO((Team)p);

	public static final Comparator<AbstractParticipant> SEEDING_COMPARATOR = (o1, o2) -> o1.getSeeding().compareTo(o2.getSeeding());
	public static final Comparator<AbstractParticipant> PLACING_COMPARATOR = (o1, o2) -> o1.getPlacing().compareTo(o2.getPlacing());

}
