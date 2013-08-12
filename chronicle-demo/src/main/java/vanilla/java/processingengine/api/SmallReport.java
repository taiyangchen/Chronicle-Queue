/*
 * Copyright 2013 Peter Lawrey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vanilla.java.processingengine.api;

import net.openhft.chronicle.Excerpt;
import net.openhft.chronicle.ExcerptMarshallable;

/**
 * @author peter.lawrey
 */
public class SmallReport implements ExcerptMarshallable {
    public CharSequence clientOrderId = new StringBuilder();
    public ReportStatus status;
    public CharSequence rejectedReason = new StringBuilder();

    public void orderOkay(CharSequence clientOrderId) {
        this.clientOrderId = clientOrderId;
        status = ReportStatus.OK;
        rejectedReason = "";
    }

    public void orderRejected(CharSequence clientOrderId, CharSequence rejectedReason) {
        this.clientOrderId = clientOrderId;
        status = ReportStatus.REJECTED;
        this.rejectedReason = rejectedReason;
    }

    @Override
    public void readMarshallable(Excerpt in) throws IllegalStateException {
        StringBuilder clientOrderId = (StringBuilder) this.clientOrderId;
        in.readUTF(clientOrderId);
        status = in.readEnum(ReportStatus.class);
        StringBuilder rejectedReason = (StringBuilder) this.rejectedReason;
        in.readUTF(rejectedReason);
    }

    @Override
    public void writeMarshallable(Excerpt out) {
        out.writeUTF(clientOrderId);
        out.writeEnum(status);
        out.writeUTF(rejectedReason);
    }
}
